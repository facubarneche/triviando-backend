package com.example.proyecto2025_BE.service.pregunta.strategy;

import com.example.proyecto2025_BE.configuration.PreguntaProperties;
import com.example.proyecto2025_BE.dao.PreguntaDao;
import com.example.proyecto2025_BE.model.Pregunta;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.Collections;
import java.util.List;
import java.util.Random;

public class InvitadoPreguntaLoader implements PreguntaLoaderStrategy {

    private final PreguntaDao preguntaDao;
    private final PreguntaProperties properties;
    private static InvitadoPreguntaLoader instance;

    public static InvitadoPreguntaLoader getInstance(PreguntaDao preguntaDao,PreguntaProperties properties) {
        if(instance == null){
            instance = new InvitadoPreguntaLoader(preguntaDao,properties);
        }
        return instance;
    }

    private InvitadoPreguntaLoader(PreguntaDao preguntaDao,PreguntaProperties properties) {
        this.preguntaDao = preguntaDao;
        this.properties = properties;
    }

    @Override
    public List<Pregunta> cargarPreguntasNoRespondidas(String topico) {
        var cantidadPreguntas = properties.getCantidad();
        List<Pregunta> preguntas = preguntaDao.findByTopico(topico).orElse(Collections.emptyList());
        return new Random().ints(0, preguntas.size())
                .distinct()
                .limit(cantidadPreguntas)
                .mapToObj(preguntas::get)
                .toList();
    }
}