package com.example.proyecto2025_BE.model.dto;

import com.example.proyecto2025_BE.constants.DatePattern;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

public record Racha(
    int rachaActual,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DatePattern.DATE)
    LocalDate ultimaActividad
) { }
