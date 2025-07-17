package com.example.proyecto2025_BE.service;

import java.util.List;

import com.example.proyecto2025_BE.model.Pregunta;
import com.example.proyecto2025_BE.model.dto.FeedbackDTO;
import com.example.proyecto2025_BE.model.dto.Topics;

public interface PreguntaService {

    Pregunta getPreguntaById(String id);
    void saveAll(List<Pregunta> preguntas);
    List<Topics> contarPreguntasPorTopicoDeUsuario(Long userId);
    boolean existsByTopicAndUser(String topic, Long userId);
    List<Pregunta> obtenerPreguntasNoRespondidasPorTopico(Long userId, String topico);
    String getTopicFromQuestion(String topic,Long userId);
    void saveFeedback(FeedbackDTO feedbackDTO);
}
