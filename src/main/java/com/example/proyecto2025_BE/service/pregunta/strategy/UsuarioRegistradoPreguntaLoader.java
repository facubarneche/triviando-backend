package com.example.proyecto2025_BE.service.pregunta.strategy;

import java.util.List;
import java.util.Random;

import com.example.proyecto2025_BE.configuration.PreguntaProperties;
import com.example.proyecto2025_BE.dao.PreguntaDao;
import com.example.proyecto2025_BE.model.Answer;
import com.example.proyecto2025_BE.model.Pregunta;
import com.example.proyecto2025_BE.model.User;
import com.example.proyecto2025_BE.service.UserService;

public class UsuarioRegistradoPreguntaLoader implements PreguntaLoaderStrategy{

    private final PreguntaDao preguntaDao;
    private final PreguntaProperties properties;
    private final Long userId;
    private final UserService userService;
    private final Random random = new Random();

    public UsuarioRegistradoPreguntaLoader(PreguntaDao preguntaDao, PreguntaProperties properties,
                                           UserService userService, Long userId) {
        this.preguntaDao = preguntaDao;
        this.properties = properties;
        this.userService = userService;
        this.userId = userId;
    }

    @Override
    public List<Pregunta> cargarPreguntasNoRespondidas(String topico) {
        var cantidadPreguntas = properties.getCantidad();
        User user = userService.retrieve(userId);
        List<String> preguntasRespondidasIds = user.getAnswers().stream()
                .map(Answer::getQuestionId)
                .toList();
       List<Pregunta> preguntasNoRespondidas =  preguntaDao.findPreguntasNotAnsweredByUserIdAndTopico(preguntasRespondidasIds, topico).stream()
                .toList();
        if (preguntasNoRespondidas.isEmpty()) {
            return List.of();
        }
        return random.ints(0, preguntasNoRespondidas.size())
                .distinct()
                .limit(Math.min(cantidadPreguntas, preguntasNoRespondidas.size()))
                .mapToObj(preguntasNoRespondidas::get)
                .toList();
    }
}