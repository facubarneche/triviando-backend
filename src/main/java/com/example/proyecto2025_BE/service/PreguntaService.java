package com.example.proyecto2025_BE.service;

import com.example.proyecto2025_BE.model.Pregunta;
import com.example.proyecto2025_BE.model.dto.PreguntaRequest;

import java.util.List;

public interface PreguntaService {

    List<Pregunta> getAllPreguntas();
    Pregunta getPreguntaById(String id);
    String createPregunta(PreguntaRequest preguntaRequest);
    List<Pregunta> getPreguntasByTopico(String topico);
}
