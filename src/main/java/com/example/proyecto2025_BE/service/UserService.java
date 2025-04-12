package com.example.proyecto2025_BE.service;


import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.proyecto2025_BE.constants.Exceptions;
import com.example.proyecto2025_BE.dao.UserDao;
import com.example.proyecto2025_BE.exceptions.ConflictException;
import com.example.proyecto2025_BE.exceptions.NotFoundException;
import com.example.proyecto2025_BE.model.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
	
	private final UserDao userDao;
	
	public void create(User user) {
		Optional<User> fetched = userDao.findByEmail(user.getEmail());
		
		if(fetched.isPresent()) {
			throw ConflictException.build("El usuario ya existe en el sistema");
		}
		
		userDao.save(user);
    }
	
	public User retrieve(Long id) {
		return userDao.findById(id)
			.orElseThrow(() -> NotFoundException.build(Exceptions.NOT_FOUND));
	}
	
	public void update(User user) {
		validateById(user.getId());
		userDao.save(user);
    }
	
	private void validateById(Long id) {
		userDao.findById(id)
			.orElseThrow(() -> NotFoundException.build(Exceptions.NOT_FOUND));
	}
	
	public void delete(Long id) {
		User user = retrieve(id);
		userDao.delete(user);
    }
	
	public User findByEmailAndPassword(User user) {
		return this.userDao.findByEmailAndPassword(user.getEmail(), user.getPassword())
				.orElseThrow(() -> NotFoundException.build(Exceptions.NOT_FOUND));
	}
}
