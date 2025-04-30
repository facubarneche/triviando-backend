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
    private List<Option> incorrectOptions;
    @JsonIgnore
    private Option correctOption;
    private String explicacion;
    private Difficulty difficulty;
    
    public BigDecimal difficultyFactor() {
    	return difficulty.getDifficultyFactor();
    }

	public boolean isSuccess(Option option) {
		return correctOption.getLetter().equals(option.getLetter());
	}
}
