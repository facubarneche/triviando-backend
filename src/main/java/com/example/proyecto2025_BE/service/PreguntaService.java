package com.example.proyecto2025_BE.service;

import java.util.List;
import java.util.Map;

import com.example.proyecto2025_BE.model.Pregunta;
import com.example.proyecto2025_BE.model.dto.PreguntaRequest;

public interface PreguntaService {

    List<Pregunta> getAllPreguntas();
    Pregunta getPreguntaById(String id);
    String createPregunta(PreguntaRequest preguntaRequest);
    List<Pregunta> getPreguntasByTopico(String topico);
    List<Pregunta> saveAll(List<Pregunta> preguntas);
    Map<String, Number> contarPreguntasPorTopico();
	boolean existsByTopic(String topic);
    List<Pregunta> obtenerPreguntasNoRespondidasPorTopico(Long userId, String topico);
}
