package com.example.proyecto2025_BE.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.example.proyecto2025_BE.constants.DatePattern;
import com.example.proyecto2025_BE.utils.racha.RachaStrategy;
import com.example.proyecto2025_BE.utils.racha.RachaStrategyFactory;
import com.example.proyecto2025_BE.views.Views;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
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
	@JsonView({Views.Score.class, Views.Login.class,Views.Ranking.class})
	private Long id;
	@JsonView(Views.Login.class)
	private String fullName;
	@Transient
	private int age;
	private String email;
	private String password;
	private String phoneNumber;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DatePattern.DATE)
	private LocalDate birthDate;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DatePattern.DATE_TIME)
	@CreationTimestamp
	private LocalDateTime createdAt;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DatePattern.DATE_TIME)
	@UpdateTimestamp
	private LocalDateTime updatedAt;
	@Builder.Default
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id")
	private List<Answer> answers = new ArrayList<>();
	@Builder.Default
	@JsonView({Views.Ranking.class,Views.Score.class})
	private BigDecimal score = BigDecimal.ZERO;
	@JsonView({Views.Login.class,Views.Ranking.class})
	private String username;
	@JsonView(Views.Racha.class)
	private Integer rachaActual;
	@JsonView(Views.Racha.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DatePattern.DATE)
	private LocalDate ultimaActividad;
	@JsonView(Views.Ranking.class)
	@Transient
	private int position;

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

	public void actualizarRacha() {
		LocalDate today = LocalDate.now();
		RachaStrategy strategy = RachaStrategyFactory.getStrategy(ultimaActividad, today);
		this.rachaActual = strategy.calcularRacha(rachaActual);
		this.ultimaActividad = today;
	}

	public Integer getRachaActual() {
		return rachaActual == null ? 0 : rachaActual;
	}

	public LocalDate getUltimaActividad() {
		return ultimaActividad == null ? createdAt.toLocalDate() : ultimaActividad;
	}
}