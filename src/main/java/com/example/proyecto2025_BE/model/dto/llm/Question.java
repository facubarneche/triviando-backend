package com.example.proyecto2025_BE.model.dto.llm;

import java.util.List;

import com.example.proyecto2025_BE.model.Difficulty;

public record Question(
		String text, 
		List<QuestionOption> options, 
		QuestionOption correctOption,
		Difficulty difficulty,
		String briefExplanationOfTheCorrectAnswer
		) { }