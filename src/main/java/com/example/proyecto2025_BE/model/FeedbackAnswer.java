package com.example.proyecto2025_BE.model;

import java.math.BigDecimal;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Representa los datos de la respuesta de la pregunta respondida")
public class FeedbackAnswer {
	
	@Schema(description = "Puntaje adquirido", example = "26")
	private BigDecimal score;
	@Schema(description = "Razón por la cual no adquirió puntos")
	private String errorReason;
	@Schema(description = "Explicación de la pregunta respondida", example = "Cristóbal Colón llegó a América en su primer viaje en este año.")
	private String explanation;
	@Schema(description = "Opcion correcta", example = "{\"letter\":\"D\",\"text\":\"1492\"}")
	private Option correctOption;
}
