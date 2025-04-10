package com.example.proyecto2025_BE.model;

import java.time.LocalDate;

import com.example.proyecto2025_BE.constants.DatePattern;
import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String fullName;
	private int age;
	private String email;
	private String password;
	private String phoneNumber;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DatePattern.DATE)
	private LocalDate birthDate;
}
