package com.example.proyecto2025_BE.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
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
	@JsonView({Views.Score.class, Views.Login.class,Views.Ranking.class, Views.Register.class})
	private Long id;
	@JsonView({Views.Login.class, Views.Register.class, Views.RegisterRequest.class})
	@NotBlank(groups = Views.RegisterRequest.class, message = "El nombre completo no puede estar vacío")
	private String fullName;
	@Transient
	private int age;
	@JsonView({Views.Register.class, Views.RegisterRequest.class})
	@NotBlank(groups = Views.RegisterRequest.class, message = "El email no puede estar vacío")
	@Email(groups = Views.RegisterRequest.class, message = "El email no tiene un formato válido")
	private String email;
	@JsonView(Views.RegisterRequest.class)
	@NotBlank(groups = Views.RegisterRequest.class, message = "La contraseña no puede estar vacía")
	private String password;
	@JsonView(Views.Register.class)
	private String phoneNumber;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DatePattern.DATE)
	@JsonView({Views.Register.class,Views.RegisterRequest.class})
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

	@JsonView(Views.Register.class)
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
}