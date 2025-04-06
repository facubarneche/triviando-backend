package com.example.proyecto2025_BE.model.dto;

import jakarta.validation.constraints.NotEmpty;

public class PreguntaRequest {

    @NotEmpty(message = "El tópico no puede estar vacío")
    String topico;
}
