package com.example.proyecto2025_BE.service;

import java.util.List;

import com.example.proyecto2025_BE.model.Pregunta;
import com.example.proyecto2025_BE.model.dto.FeedbackDTO;
import com.example.proyecto2025_BE.model.dto.PreguntaRequest;
import com.example.proyecto2025_BE.model.dto.Topics;
import reactor.core.publisher.Mono;

public interface PreguntaService {

    List<Pregunta> getAllPreguntas();
    Pregunta getPreguntaById(String id);
    String createPregunta(PreguntaRequest preguntaRequest);
    List<Pregunta> getPreguntasByTopico(String topico);
    List<Pregunta> saveAll(List<Pregunta> preguntas);
    List<Topics> contarPreguntasPorTopico();
    List<Topics> contarPreguntasPorTopico(long userId);
	boolean existsByTopic(String topic);
    List<Pregunta> obtenerPreguntasNoRespondidasPorTopico(Long userId, String topico);
    String getTopicFromQuestion(String topico);
    void saveFeedback(FeedbackDTO feedbackDTO);
}
