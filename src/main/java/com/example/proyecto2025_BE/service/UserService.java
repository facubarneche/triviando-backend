package com.example.proyecto2025_BE.service;


import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

import com.example.proyecto2025_BE.exceptions.ValidationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.proyecto2025_BE.constants.Exceptions;
import com.example.proyecto2025_BE.dao.RespuestasDao;
import com.example.proyecto2025_BE.dao.UserDao;
import com.example.proyecto2025_BE.exceptions.ConflictException;
import com.example.proyecto2025_BE.exceptions.NotFoundException;
import com.example.proyecto2025_BE.model.User;
import com.example.proyecto2025_BE.model.dto.Ranking;
import com.example.proyecto2025_BE.model.dto.StatsResponse;
import com.example.proyecto2025_BE.utils.JsonViewPage;
import com.example.proyecto2025_BE.utils.UserRankingProjection;

import lombok.RequiredArgsConstructor;



@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

	private final UserDao userDao;
	private final RespuestasDao answerDao;

	public User create(User user) {
		Optional<User> fetched = userDao.findByEmail(user.getEmail());

		if (userDao.findByEmail(user.getEmail()).isPresent()) {
			throw ConflictException.build("El email '" + user.getEmail() + "' ya está en uso.");
		}

		if (userDao.findByUsername(user.getUsername()).isPresent()) {
			throw ConflictException.build("El nombre de usuario '" + user.getUsername() + "' ya está en uso.");
		}

		return userDao.save(user);
	}

	@Transactional(readOnly = true)
	public User retrieve(Long id) {
		return userDao.findById(id)
				.orElseThrow(() -> NotFoundException.build(Exceptions.NOT_FOUND));
	}

	public User update(User updatedUser) {
		User existingUser = ValidarUsuario(updatedUser);

		Optional.ofNullable(updatedUser.getName()).ifPresent(existingUser::setName);
		Optional.ofNullable(updatedUser.getLastName()).ifPresent(existingUser::setLastName);
		Optional.ofNullable(updatedUser.getPhoneNumber()).ifPresent(existingUser::setPhoneNumber);
		Optional.ofNullable(updatedUser.getCountryCode()).ifPresent(existingUser::setCountryCode);
		Optional.ofNullable(updatedUser.getJoinDate()).ifPresent(existingUser::setJoinDate);

		return userDao.save(existingUser);
	}

	private User ValidarUsuario(User updatedUser) {
		Long userId = updatedUser.getId();
		if (userId == null) {
			throw new ValidationException("El ID del usuario es requerido para la actualización.");
		}

		User existingUser = this.retrieve(userId);

		validarCampoUnico(
				updatedUser.getUsername(),
				existingUser.getUsername(),
				"nombre de usuario",
				userDao::findByUsername,
				existingUser::setUsername);

		validarCampoUnico(
				updatedUser.getEmail(),
				existingUser.getEmail(),
				"email",
				userDao::findByEmail,
				existingUser::setEmail);

		return existingUser;
	}

	private <T> void validarCampoUnico(T newValue, T existingValue, String fieldName, Function<T, Optional<User>> findByMethod,
										 Consumer<T> setterMethod) {
		Optional.ofNullable(newValue)
				.filter(value -> !String.valueOf(value).isBlank() && !value.equals(existingValue))
				.ifPresent(value -> {
					findByMethod.apply(value).ifPresent(user -> {
						throw new ValidationException("El " + fieldName + " '" + value + "' ya está en uso.");
					});
					setterMethod.accept(value);
				});
	}

	public void delete(Long id) {
		User user = retrieve(id);
		userDao.delete(user);
	}

	@Transactional(readOnly = true)
	public User findByEmailAndPassword(User loginInfo) {
		return this.userDao.findByEmailAndPassword(loginInfo.getEmail(), loginInfo.getPassword())
				.orElseThrow(() -> NotFoundException.build(Exceptions.NOT_FOUND));
	}

	public Page<User> getUsersOrderedByScoreDesc(int page,int size ) {
		Pageable pageable = createPageable(page, size);
		Page<UserRankingProjection> projectionPage = userDao.findAllUsersWithRank(pageable);
		List<User> users = projectionPage.getContent().stream()
				.map(this::convertProjectionToUser)
				.toList();
		return new JsonViewPage<>(users, projectionPage.getPageable(), projectionPage.getTotalElements());
	}

	public Page<User> getUsersOrderedByScoreFromUser(Long userId, int page,int size) {
		this.retrieve(userId);
		Integer userPosition = userDao.findUserRankPosition(userId);
		if (userPosition == null || userPosition <= 0) {
			return getUsersOrderedByScoreDesc(page,size);
		}
		int zeroBasedPosition = userPosition - 1;
		int pageNumber = zeroBasedPosition / size;//3
		return getUsersOrderedByScoreDesc(pageNumber,size);
	}

	@Transactional(readOnly = true)
	public Page<Ranking> getWeeklyRanking(int page, int size) {
		Pageable pageable = createPageable(page, size);
		Page<UserRankingProjection> projectionPage = userDao.findWeeklyRanking(pageable);
		List<Ranking> users = projectionPage.getContent().stream()
				.map(this::convertProjectionToRankin)
				.toList();
		return new PageImpl<>(users, pageable, projectionPage.getTotalElements());
	}

	@Transactional(readOnly = true)
	public Page<Ranking> getWeeklyRanking(Long userId, int page, int size) {
		this.retrieve(userId);
		Integer userPosition = userDao.findUserRankWeeklyPosition(userId);
		if (userPosition == null || userPosition <= 0) {
			return getWeeklyRanking(page,size);
		}
		int zeroBasedPosition = userPosition - 1;
		int pageNumber = zeroBasedPosition / size;
		return getWeeklyRanking(pageNumber,size);
	}

	private User convertProjectionToUser(UserRankingProjection projection) {

		User user = userDao.findById(projection.getId())
				.orElseGet(User::new); // Fallback a un nuevo User si por alguna razón no existe
		user.setPosition(projection.getPosition());
		return user;
	}
	private Ranking convertProjectionToRankin(UserRankingProjection projection) {
		return new Ranking(projection.getId(), projection.getUserName(),projection.getScore().doubleValue(),projection.getPosition() );
	}

	private Pageable createPageable(int page, int size) {
			return PageRequest.of(page, size);
	}

	public StatsResponse getStatisticsFromUser(Long userId) {
		var totalQuestions = answerDao.countByUserId(userId);
		var correctAnswers = answerDao.countByUserIdAndErrorReasonIsNull(userId);
		var totalQuizzes = totalQuestions / 5;
		return new StatsResponse(totalQuizzes, correctAnswers, totalQuestions);
	}
}