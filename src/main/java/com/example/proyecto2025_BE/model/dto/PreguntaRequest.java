package com.example.proyecto2025_BE.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public class PreguntaRequest {

    @NotEmpty(message = "El enunciado no puede estar vacío")
    String enunciado;
    @NotEmpty(message = "El tópico no puede estar vacío")
    String topico;
    @NotNull(message = "Las opciones no pueden ser nulas")
    @Size(min = 2, message = "Debe haber al menos 2 opciones")
    List<String> opciones;
    @NotNull(message = "La respuesta correcta no puede ser nula")
    int respuestaCorrecta;

}
