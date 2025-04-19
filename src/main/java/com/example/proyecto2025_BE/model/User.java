package com.example.proyecto2025_BE.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;

import com.example.proyecto2025_BE.constants.DatePattern;
import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

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
	@Transient
	private int age;
	private String email;
	private String password;
	private String phoneNumber;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DatePattern.DATE)
	private LocalDate birthDate;
	@CreationTimestamp
	private LocalDateTime createdAt;
	@UpdateTimestamp
	private LocalDateTime updatedAt;

	public Integer getAge(){
		if (birthDate == null){
			return null;
		}
		return Period.between(birthDate, LocalDate.now()).getYears();
	}
}
