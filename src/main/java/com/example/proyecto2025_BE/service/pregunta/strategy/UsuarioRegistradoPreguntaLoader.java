package com.example.proyecto2025_BE.service.pregunta.strategy;

import com.example.proyecto2025_BE.configuration.PreguntaProperties;
import com.example.proyecto2025_BE.dao.PreguntaDao;
import com.example.proyecto2025_BE.model.Answer;
import com.example.proyecto2025_BE.model.Pregunta;
import com.example.proyecto2025_BE.model.User;
import com.example.proyecto2025_BE.service.UserService;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class UsuarioRegistradoPreguntaLoader implements PreguntaLoaderStrategy{

    private final PreguntaDao preguntaDao;
    private final PreguntaProperties properties;
    private final Long userId;
    private final UserService userService;
    private static UsuarioRegistradoPreguntaLoader instance;
    private final Random random;


    public static UsuarioRegistradoPreguntaLoader getInstance(PreguntaDao preguntaDao,PreguntaProperties properties,UserService userService, Long userId ){
        if(instance == null){
            instance = new UsuarioRegistradoPreguntaLoader(preguntaDao,properties,userService, userId);
        }
        return instance;
    }

    private UsuarioRegistradoPreguntaLoader(PreguntaDao preguntaDao, PreguntaProperties properties,UserService userService,Long userId) {
        this.preguntaDao = preguntaDao;
        this.properties = properties;
        this.userService = userService;
        this.userId = userId;
        this.random = new Random();
    }

    @Value("${api.preguntas.cantidad}")
    private int cantidadPreguntas;

    @Override
    public List<Pregunta> cargarPreguntasNoRespondidas(String topico) {
        var cantidadPreguntas = properties.getCantidad();
        User user = userService.retrieve(userId);
        List<String> preguntasRespondidasIds = user.getAnswers().stream()
                .map(Answer::getQuestionId)
                .toList();
       List<Pregunta> preguntasNoRespondidas =  preguntaDao.findPreguntasNotAnsweredByUserIdAndTopico(preguntasRespondidasIds, topico).stream()
                .toList();
        return random.ints(0, preguntasNoRespondidas.size())
                .distinct() // Asegura que no se repitan las preguntas seleccionadas
                .limit(cantidadPreguntas)
                .mapToObj(preguntasNoRespondidas::get)
                .collect(Collectors.toList());
    }
}