package com.example.proyecto2025_BE.service;

import com.example.proyecto2025_BE.dao.PreguntaDao;
import com.example.proyecto2025_BE.exceptions.NotFoundException;
import com.example.proyecto2025_BE.model.Pregunta;
import com.example.proyecto2025_BE.model.dto.PreguntaRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PreguntaServiceImpl implements PreguntaService{

    private final PreguntaDao preguntaDao;


    @Override
    public List<Pregunta> getAllPreguntas() {
        return preguntaDao.findAll();
    }

    @Override
    public Pregunta getPreguntaById(String id) {
        return preguntaDao
                .findById(id)
                .orElseThrow(() -> new NotFoundException("No se encontro la pregunta con id: " + id));
    }

    @Override
    public String createPregunta(PreguntaRequest preguntaRequest) {
        return "this action should create a new pregunta";
    }

    @Override
    public List<Pregunta> getPreguntasByTopico(String topico) {
        return preguntaDao
                .findByTopico(topico)
                .flatMap(lista -> lista.isEmpty() ? Optional.empty() : Optional.of(lista))
                .orElseThrow(() -> new NotFoundException("No se encontraron preguntas con topico: " + topico));
    }
}
