package com.example.proyecto2025_BE.service.pregunta.strategy;

import com.example.proyecto2025_BE.model.Pregunta;

import java.util.List;

public interface PreguntaLoaderStrategy {
    List<Pregunta> cargarPreguntasNoRespondidas( String topico);
}
