package com.example.proyecto2025_BE.model.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class PreguntaRequest {

    @NotEmpty(message = "El tópico no puede estar vacío")
    String topico;
}
