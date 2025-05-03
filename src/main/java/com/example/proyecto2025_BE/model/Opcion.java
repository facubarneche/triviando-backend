package com.example.proyecto2025_BE.model;


import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Opcion {
	@Enumerated(EnumType.STRING)
	private LetterOption letter;
	private String text;
}
