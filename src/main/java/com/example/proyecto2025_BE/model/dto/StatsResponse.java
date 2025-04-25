package com.example.proyecto2025_BE.model.dto;

public record StatsResponse(
    int totalQuizzes,
    int correctAnswers,
    int totalQuestions
) {
}
