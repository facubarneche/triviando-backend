package com.example.proyecto2025_BE.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.List;

import com.example.proyecto2025_BE.constants.DatePattern;
import com.example.proyecto2025_BE.views.Views;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;

import java.util.ArrayList;
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
	@JsonView(Views.Score.class)
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
	@Builder.Default
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id")
	private List<Answer> answers = new ArrayList<>();
	@Builder.Default
	@JsonView(Views.Score.class)
	private BigDecimal score = BigDecimal.ZERO;

	public Integer getAge() {
		if (birthDate == null){
			return null;
		}
		return Period.between(birthDate, LocalDate.now()).getYears();
	}

	public void add(Answer answer) {
		answers.add(answer);
	}
	
	public void add(BigDecimal score) {
		this.score = this.score.add(score);
	}
}
