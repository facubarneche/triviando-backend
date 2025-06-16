package com.example.proyecto2025_BE.service;

import java.util.List;
import java.util.Optional;

import com.example.proyecto2025_BE.constants.Exceptions;
import com.example.proyecto2025_BE.model.dto.Topics;
import org.springframework.stereotype.Service;
import com.example.proyecto2025_BE.dao.PreguntaDao;
import com.example.proyecto2025_BE.exceptions.NotFoundException;
import com.example.proyecto2025_BE.model.Answer;
import com.example.proyecto2025_BE.model.Pregunta;
import com.example.proyecto2025_BE.model.dto.PreguntaRequest;
import com.example.proyecto2025_BE.service.pregunta.factory.PreguntaLoaderFactory;
import com.example.proyecto2025_BE.service.pregunta.strategy.PreguntaLoaderStrategy;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PreguntaServiceImpl implements PreguntaService {

    private final PreguntaDao preguntaDao;
    private final UserService userService;
    private final PreguntaLoaderFactory preguntaLoaderFactory;

    @Override
    public List<Pregunta> getAllPreguntas() {
        return preguntaDao.findAll();
    }

    @Override
    public Pregunta getPreguntaById(String id) {
        return preguntaDao
                .findById(id)
                .orElseThrow(() -> NotFoundException.build("No se encontro la pregunta con id: " + id));
    }

    @Override
    public String createPregunta(PreguntaRequest preguntaRequest) {
        return "this action should create a new pregunta";
    }

    @Override
    public List<Pregunta> getPreguntasByTopico(String topico) {
        return preguntaDao
                .findByTopico(topico)
                .orElseThrow(() -> NotFoundException.build("No se encontraron preguntas con topico: " + topico));
    }

    @Override
    public List<Topics> contarPreguntasPorTopico() {
         return preguntaDao.contarPreguntasPorTopico().stream()
                .map(topic ->
                        Topics.builder()
                                .topic((String) topic.get("_id"))
                                .size((Number) topic.get("cantidadPreguntas"))
                                .emoji((String) topic.get("emoji"))
                                .build())
                .toList();
    }

    @Override
    public List<Topics> contarPreguntasPorTopico(long userId) {
        var user = userService.retrieve(userId);

        List<String> idPreguntas = user.getAnswers().stream()
                .map(Answer::getQuestionId)
                .toList();

        return preguntaDao.contarPreguntasPorTopicoIncluyendoRespondidas(idPreguntas).stream()
                .map(topic ->
                        Topics.builder()
                                .topic((String) topic.get("_id"))
                                .size((Number) topic.get("cantidadPreguntas"))
                                .emoji((String) topic.get("emoji"))
                                .build())
                .toList();
    }

    @Override
    public List<Pregunta> saveAll(List<Pregunta> preguntas) {
    	return preguntaDao.saveAll(preguntas);
    }
    
    @Override
    public boolean existsByTopic(String topic) {
    	return preguntaDao.existsByTopico(topic);
    }

    @Override
    public List<Pregunta> obtenerPreguntasNoRespondidasPorTopico(Long userId, String topico) {
        PreguntaLoaderStrategy preguntaLoader = preguntaLoaderFactory.getPreguntaLoader(userId);
        return preguntaLoader.cargarPreguntasNoRespondidas(topico);
    }

    @Override
    public String getTopicFromQuestion(String topico) {
        return preguntaDao.getFirstByTopico(topico).orElseThrow(() -> NotFoundException.build(Exceptions.NOT_FOUND)).getEmoji();
    }
}