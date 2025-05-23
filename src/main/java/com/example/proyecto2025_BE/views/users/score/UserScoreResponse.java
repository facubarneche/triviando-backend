package com.example.proyecto2025_BE.views.users.score;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@Schema(description = "Respuesta de la puntuación de un usuario")
public class UserScoreResponse {
    @Schema(description = "ID del usuario", example = "12345")
    private long id;
    @Schema(description = "Puntaje del usuario", example = "400")
    private BigDecimal score;
}
