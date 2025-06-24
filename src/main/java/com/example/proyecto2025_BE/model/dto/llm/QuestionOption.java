package com.example.proyecto2025_BE.model.dto.llm;

import com.example.proyecto2025_BE.model.LetterOption;

public record QuestionOption(
		String text,
		LetterOption letter
		) { }
