package com.example.proyecto2025_BE.model;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Document(collection = "preguntas")
@NoArgsConstructor
@AllArgsConstructor
public class Pregunta {

    @Id
    private String id;
    private String topico;
    private Long userId;
    private String emoji;
    private String enunciado;
    private List<Option> options;
    @JsonIgnore
    private Option correctOption;
    private String explicacion;
    private Difficulty difficulty;
    @CreatedDate
    private LocalDateTime createdAt;
    
    public BigDecimal difficultyFactor() {
    	return difficulty.getDifficultyFactor();
    }

	public boolean isSuccess(LetterOption optionSelected) {
		return correctOption.getLetter().equals(optionSelected);
	}
}
