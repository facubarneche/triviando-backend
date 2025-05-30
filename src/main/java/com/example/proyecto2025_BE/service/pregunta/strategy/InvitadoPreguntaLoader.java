package com.example.proyecto2025_BE.service.pregunta.strategy;

import com.example.proyecto2025_BE.configuration.PreguntaProperties;
import com.example.proyecto2025_BE.dao.PreguntaDao;
import com.example.proyecto2025_BE.model.Pregunta;

import java.util.Collections;
import java.util.List;
import java.util.Random;

    public class InvitadoPreguntaLoader implements PreguntaLoaderStrategy {

        private PreguntaDao preguntaDao;
        private PreguntaProperties properties;

        public InvitadoPreguntaLoader init(PreguntaDao preguntaDao, PreguntaProperties properties) {
            this.preguntaDao = preguntaDao;
            this.properties = properties;
            return this;
        }

        @Override
        public List<Pregunta> cargarPreguntasNoRespondidas(String topico) {
            int cantidad = properties.getCantidad();
            List<Pregunta> preguntas = preguntaDao.findByTopico(topico).orElse(Collections.emptyList());
            if (preguntas.isEmpty()) return List.of();
            return new Random().ints(0, preguntas.size())
                    .distinct()
                    .limit(cantidad)
                    .mapToObj(preguntas::get)
                    .toList();
        }
    }