package com.example.proyecto2025_BE.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.proyecto2025_BE.model.User;

@Repository
public interface UserDao extends JpaRepository<User, Long>{

	public Optional<User> findByEmailAndPassword(String email, String password);
	
	public Optional<User> findByEmail(String email);
}
