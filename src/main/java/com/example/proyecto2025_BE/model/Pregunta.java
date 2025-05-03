package com.example.proyecto2025_BE.model;


import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Document(collection = "preguntas")
public class Pregunta {

    @Id
    private String id;
    private String topico;
    private String enunciado;
    private List<Opcion> incorrectOptions;
    @JsonIgnore
    private Opcion correctOption;
    private String explicacion;
    private Difficulty difficulty;
    
    public BigDecimal difficultyFactor() {
    	return difficulty.getDifficultyFactor();
    }

	public boolean isSuccess(LetterOption letterSelected) {
		return correctOption.getLetter().equals(letterSelected);
	}
}
