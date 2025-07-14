package com.example.proyecto2025_BE.views.questions.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@Schema(description = "Datos para generar un set de preguntas sobre determinado topico")
public class GenerateRequestBody {
    @Schema(description = "Tópico de generacón", example = "Inteligencia Artificial y Aprendizaje Automático")
    private String topic;
    @Schema(description = "Id del usuario logueado")
    private Long userId;
    @Schema(description = "Strategy de prompt", example = "questionPrompter | topicPrompter")
    private String promptType;
}
