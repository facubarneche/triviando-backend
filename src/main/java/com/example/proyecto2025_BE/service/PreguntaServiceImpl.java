package com.example.proyecto2025_BE.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.proyecto2025_BE.dao.PreguntaDao;
import com.example.proyecto2025_BE.exceptions.NotFoundException;
import com.example.proyecto2025_BE.model.Pregunta;
import com.example.proyecto2025_BE.model.dto.PreguntaRequest;

import lombok.RequiredArgsConstructor;

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
                .flatMap(lista -> lista.isEmpty() ? Optional.empty() : Optional.of(lista))
                .orElseThrow(() -> NotFoundException.build("No se encontraron preguntas con topico: " + topico));
    }

    @Override
    public Map<String, Number> contarPreguntasPorTopico() {
        return preguntaDao.contarPreguntasPorTopico().stream()
                .collect(Collectors.toMap(
                        m -> (String) m.get("_id"),
                        m -> (Number) m.get("cantidadPreguntas")
                ));
    }
}