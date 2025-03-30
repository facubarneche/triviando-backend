package com.example.proyecto2025_BE.service;

import com.example.proyecto2025_BE.dao.PreguntaDao;
import com.example.proyecto2025_BE.exceptions.NotFoundException;
import com.example.proyecto2025_BE.model.Pregunta;
import com.example.proyecto2025_BE.model.dto.PreguntaRequest;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PreguntasService {

    @Autowired
    private PreguntaDao preguntaDao;

    public List<Pregunta> getAllPreguntas() {
        return preguntaDao
                .findAll();
    }

    public Pregunta getPreguntaById(String id) {
        return preguntaDao
                .findById(id)
                .orElseThrow(() -> new NotFoundException("No se encontro la pregunta con id: " + id));
    }

    public List<Pregunta> getPreguntasByTopico(String topico) {
        return preguntaDao
                .findByTopico(topico)
                .flatMap(lista -> lista.isEmpty() ? Optional.empty() : Optional.of(lista))
                .orElseThrow(() -> new NotFoundException("No se encontraron preguntas con topico: " + topico));
    }


    public String createPregunta(PreguntaRequest preguntaRequest) {
        return "this action should create a new pregunta";
//        return preguntaDao.save(new Pregunta());
    }
}
