package com.example.proyecto2025_BE.service;


import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
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
import com.example.proyecto2025_BE.model.User;
import com.example.proyecto2025_BE.model.dto.Racha;
import com.example.proyecto2025_BE.model.dto.UserRankingDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
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
				case "email" -> user.setEmail(value.toString());
				case "password" -> user.setPassword(value.toString());
				case "birthday" -> user.setBirthDate(LocalDate.parse(value.toString()));
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

	public Page<UserRankingDTO> getUsersOrderedByScoreDesc(Pageable pageable) {
		PageRequest internalPageable = PageRequest.of(
				pageable.getPageNumber(),
				pageable.getPageSize(),
				Sort.by(Sort.Direction.DESC, "score")
						.and(Sort.by(Sort.Direction.ASC, "id")));

		Page<User> userPage = userDao.findAll(internalPageable);
		List<UserRankingDTO> dtoList = userPage.getContent().stream()
				.map(UserRankingDTO::fromUser)
				.collect(Collectors.toList());

		return new PageImpl<>(
				dtoList,
				PageRequest.of(userPage.getNumber() + 1, userPage.getSize(), userPage.getSort()),
				userPage.getTotalElements()
		);
	}

	public Page<UserRankingDTO> getUsersOrderedByScoreFromUser(Long userId, Pageable pageable) {
		Integer userPosition = userDao.findUserRankPosition(userId);
		if (userPosition == null) {
			// Si no encontramos la posición, devolvemos la primera página
			return getUsersOrderedByScoreDesc(PageRequest.of(0, pageable.getPageSize()));
		}
		int pageNumber = userPosition / pageable.getPageSize();
		return getUsersOrderedByScoreDesc(PageRequest.of(pageNumber, pageable.getPageSize()));
	}

	public Racha getRachaUsuario(Long userId) {
		User user = retrieve(userId);
		return new Racha(user.getRachaActual(), user.getUltimaActividad());
	}
}
