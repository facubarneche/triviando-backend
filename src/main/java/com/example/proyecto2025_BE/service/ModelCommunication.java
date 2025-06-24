package com.example.proyecto2025_BE.service;

import com.example.proyecto2025_BE.model.dto.llm.QuestionList;

public interface ModelCommunication {

    QuestionList generateQuestions(String message);

    String generateEmoji(String message);
}
