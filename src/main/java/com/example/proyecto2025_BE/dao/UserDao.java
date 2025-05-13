package com.example.proyecto2025_BE.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.proyecto2025_BE.model.User;

@Repository
public interface UserDao extends JpaRepository<User, Long>{

	public Optional<User> findByEmailAndPassword(String email, String password);

	public Optional<User> findByEmail(String email);

	@Query(value =
			"SELECT position - 1 FROM (" +
					"  SELECT id, ROW_NUMBER() OVER (ORDER BY score DESC, id ASC) as position " +
					"  FROM users" +
					") ranked WHERE ranked.id = :userId",
			nativeQuery = true)
	Integer findUserRankPosition(@Param("userId") Long userId);
}
