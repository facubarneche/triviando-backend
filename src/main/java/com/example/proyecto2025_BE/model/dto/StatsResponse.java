package com.example.proyecto2025_BE.model.dto;


import io.swagger.v3.oas.annotations.media.Schema;
@Schema(description = "Estadísticas de un usuario")
public record StatsResponse(
        @Schema(description = "El total  de preguntas respondidas", example = "5")
        int totalQuizzes,
        @Schema(description = "El total de preguntas correctas", example = "3")
        int correctAnswers,
        @Schema(description = "El total de preguntas", example = "10")
        int totalQuestions) { }