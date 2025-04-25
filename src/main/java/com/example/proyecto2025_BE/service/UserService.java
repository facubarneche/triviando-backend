package com.example.proyecto2025_BE.service;


import java.time.LocalDate;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.proyecto2025_BE.constants.Exceptions;
import com.example.proyecto2025_BE.dao.UserDao;
import com.example.proyecto2025_BE.exceptions.ConflictException;
import com.example.proyecto2025_BE.exceptions.NotFoundException;
import com.example.proyecto2025_BE.exceptions.ValidationException;
import com.example.proyecto2025_BE.model.User;
import com.example.proyecto2025_BE.model.dto.login.LoginRequestDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
	
	private final UserDao userDao;

	@Transactional
	public User create(User user) {
		Optional<User> fetched = userDao.findByEmail(user.getEmail());
		
		if(fetched.isPresent()) {
			throw ConflictException.build("El usuario ya existe en el sistema");
		}
		
		return userDao.save(user);
    }

	@Transactional(readOnly = true)
	public User retrieve(Long id) {
		return userDao.findById(id)
			.orElseThrow(() -> NotFoundException.build(Exceptions.NOT_FOUND));
	}

	@Transactional
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

	@Transactional
	public void delete(Long id) {
		User user = retrieve(id);
		userDao.delete(user);
    }

	@Transactional(readOnly = true)
	public User findByEmailAndPassword(LoginRequestDTO loginInfo) {
		return this.userDao.findByEmailAndPassword(loginInfo.getEmail(), loginInfo.getPassword())
				.orElseThrow(() -> NotFoundException.build(Exceptions.NOT_FOUND));
	}


}
