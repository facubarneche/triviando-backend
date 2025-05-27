package com.example.proyecto2025_BE.model;

import com.example.proyecto2025_BE.constants.UnsuccessReasons;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.time.LocalDate;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Answer {

    private static final long LATENCY_PENALTY_LIMIT = 30000;
    private static final long LATENCY_UNSUCCESSFULL_LIMIT = 50000;
    private static final BigDecimal PENALTY_FACTOR = BigDecimal.valueOf(0.1);
    private static final BigDecimal BASE_SCORE = BigDecimal.valueOf(LATENCY_PENALTY_LIMIT * 2);
    private static final MathContext DECIMAL_SCALE = new MathContext(1, RoundingMode.HALF_UP);
    private static final MathContext ROUNDING_MODE = new MathContext(0, RoundingMode.HALF_UP);

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;
    private String questionId;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @Enumerated(EnumType.STRING)
    private LetterOption optionSelected;
    private long millisecondsSpent;
    @JsonIgnore
    private String errorReason;
    private LocalDate fechaRespuesta;

    public BigDecimal getScoreBy(Pregunta question) {
        if (isSuccess(question)) {
            return calculateScoreBy(question);
        }
        errorReason = errorReason != null ? errorReason : UnsuccessReasons.INCORRECT_OPTION;
        return BigDecimal.ZERO;
    }

    private BigDecimal calculateScoreBy(Pregunta question) {
        BigDecimal scoreTemp = isPenalized() ? penalizedScore() : baseScore();

        return question.difficultyFactor().multiply(scoreTemp);
    }

    private boolean isSuccess(Pregunta question) {
        if (isLatencyLimitExeeded()) {
            errorReason = UnsuccessReasons.RESPONSE_TIME_EXEEDED;
        }

        return question.isSuccess(optionSelected) && !isLatencyLimitExeeded();
    }

    private boolean isLatencyLimitExeeded() {
        return millisecondsSpent > LATENCY_UNSUCCESSFULL_LIMIT;
    }

    private BigDecimal penalizedScore() {
        return baseScore().multiply(PENALTY_FACTOR);
    }

    private BigDecimal baseScore() {
        return BASE_SCORE.divide(BigDecimal.valueOf(millisecondsSpent), DECIMAL_SCALE).round(ROUNDING_MODE);
    }

    private boolean isPenalized() {
        return millisecondsSpent >= LATENCY_PENALTY_LIMIT;
    }
}