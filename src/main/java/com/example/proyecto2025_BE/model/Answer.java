package com.example.proyecto2025_BE.model;

import java.math.BigDecimal;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Transient;
import lombok.Data;

@Data
@Entity
public class Answer {
	
	private static final long LATENCY_PENALTY_LIMIT = 30000;
	private static final long LATENCY_UNSUCCESSFULL_LIMIT = 50000;
	private static final BigDecimal PENALTY_FACTOR = BigDecimal.valueOf(0.1);
	private static final BigDecimal BASE_SCORE = BigDecimal.valueOf(LATENCY_PENALTY_LIMIT * 2);
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String questionId;
	@Transient
	private Long userId;
	@OneToOne(cascade = CascadeType.ALL)
	private Opcion optionSelected;
	private long millisecondsSpent;	// TODO: Me gustaria que no lo mande al FE. Es parte del calculo del puntaje.
	
	public BigDecimal getScoreBy(Pregunta question) {
		return isSuccess(question) ? calculateScoreBy(question) : BigDecimal.ZERO;
	}
	
	private BigDecimal calculateScoreBy(Pregunta question) {
		BigDecimal scoreTemp = isPenalized() ? penalizedScore() : baseScore();
		
		return question.difficultyFactor().multiply(scoreTemp);
	}
	
	private boolean isSuccess(Pregunta question) {
		return question.isSuccess(optionSelected) && LATENCY_UNSUCCESSFULL_LIMIT > millisecondsSpent;
	}
	
	private BigDecimal penalizedScore() {
		return baseScore().multiply(PENALTY_FACTOR);
	}
	
	private BigDecimal baseScore() {
		return BASE_SCORE.divide(BigDecimal.valueOf(millisecondsSpent));
	}
	
	private boolean isPenalized() {
		return millisecondsSpent >= LATENCY_PENALTY_LIMIT;
	}
}
