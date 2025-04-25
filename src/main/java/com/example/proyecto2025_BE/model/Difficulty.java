package com.example.proyecto2025_BE.model;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Difficulty {
	LOW(BigDecimal.valueOf(5)),
	MEDIUM(BigDecimal.valueOf(8)),
	HIGH(BigDecimal.valueOf(13));
	
	private final BigDecimal difficultyFactor;
}
