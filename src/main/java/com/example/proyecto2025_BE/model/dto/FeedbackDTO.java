package com.example.proyecto2025_BE.model.dto;

import com.example.proyecto2025_BE.model.Feedback;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record FeedbackDTO(String id,
                          @NotNull(message = "El userId  no puede ser nulo ")
                          Integer userId,
                          @NotBlank(message = "El questionId no puede ser nulo o vacío")
                          String questionId,
                          @NotNull(message = "El questionId no puede ser nulo o vacío")
                          Feedback.FeedbackOption feedbackType,
                          String description) {}