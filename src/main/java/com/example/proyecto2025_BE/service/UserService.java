package com.example.proyecto2025_BE.service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.example.proyecto2025_BE.model.User;
import com.example.proyecto2025_BE.utils.JsonViewPage;
import com.example.proyecto2025_BE.utils.UserRankingProjection;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.proyecto2025_BE.constants.Exceptions;
import com.example.proyecto2025_BE.dao.UserDao;
import com.example.proyecto2025_BE.exceptions.ConflictException;
import com.example.proyecto2025_BE.exceptions.NotFoundException;
import com.example.proyecto2025_BE.exceptions.ValidationException;



@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

	private final UserDao userDao;

	public User create(User user) {
		Optional<User> fetched = userDao.findByEmail(user.getEmail());

		if (fetched.isPresent()) {
			throw ConflictException.build("El usuario ya existe en el sistema");
		}

		return userDao.save(user);
	}

	@Transactional(readOnly = true)
	public User retrieve(Long id) {
		return userDao.findById(id)
				.orElseThrow(() -> NotFoundException.build(Exceptions.NOT_FOUND));
	}

	public User update(User user, Map<String, Object> properties) {

		properties.forEach((key, value) -> {
			switch (key) {
				case "fullName" -> user.setFullName(value.toString());
				case "username" -> user.setUsername(value.toString());
				case "email" -> user.setEmail(value.toString());
				case "password" -> user.setPassword(value.toString());
				case "birthDate" -> user.setBirthDate(LocalDate.parse(value.toString()));
				case "phoneNumber" -> user.setPhoneNumber(value.toString());
				default -> throw ValidationException.build("La propiedad no existe o no puede ser modificada");
			}
		});

		return userDao.save(user);
	}

	public User update(User user) {
		return userDao.save(user);
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

	public Page<User> getUsersOrderedByScoreDesc(int page,int size, String[] sort) {

		Pageable pageable = createPageable(page, size, sort);
		Page<UserRankingProjection> projectionPage = userDao.findAllUsersWithRank(pageable);
		List<User> users = projectionPage.getContent().stream()
				.map(this::convertProjectionToUser)
				.toList();
		return new JsonViewPage<>(users, projectionPage.getPageable(), projectionPage.getTotalElements());
	}

	public Page<User> getUsersOrderedByScoreFromUser(Long userId, int page,int size, String[] sort) {
		this.retrieve(userId);
		Integer userPosition = userDao.findUserRankPosition(userId);

		if (userPosition == null || userPosition <= 0) {
			return getUsersOrderedByScoreDesc(page,size,sort);
		}
		int zeroBasedPosition = userPosition - 1;
		int pageNumber = zeroBasedPosition / size;
		return getUsersOrderedByScoreDesc(pageNumber,size,sort);
	}

	private User convertProjectionToUser(UserRankingProjection projection) {
		User user = userDao.findById(projection.getId())
				.orElseGet(User::new); // Fallback a un nuevo User si por alguna razón no existe
		user.setPosition(projection.getPosition());
		return user;
	}

	private Pageable createPageable(int page, int size, String[] sortParams) {
		if (sortParams == null || sortParams.length == 0) {
			Sort defaultSort = Sort.by("score").descending().and(Sort.by("id").ascending());
			return PageRequest.of(page, size, defaultSort);
		}

		List<Sort.Order> orders = Arrays.stream(sortParams)
				.map(param -> param.split(",", 2))
				.map(parts -> new Sort.Order(
						parts.length > 1 && parts[1].equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC,
						parts[0]
				))
				.toList();

		return PageRequest.of(page, size, Sort.by(orders));
	}
}
