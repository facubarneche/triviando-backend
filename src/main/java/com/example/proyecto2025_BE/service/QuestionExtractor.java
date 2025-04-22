package com.example.proyecto2025_BE.service;

import com.example.proyecto2025_BE.model.dto.Question;

public interface QuestionExtractor {
	Question extractQuestionFrom(String text);
}
