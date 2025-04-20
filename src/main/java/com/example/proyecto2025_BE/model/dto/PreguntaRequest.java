package com.example.proyecto2025_BE.model.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PreguntaRequest {

    @NotEmpty(message = "El tópico no puede estar vacío")
    String topico;
}
