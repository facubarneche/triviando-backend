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
import com.example.proyecto2025_BE.service.pregunta.factory.PreguntaLoaderFactory;
import com.example.proyecto2025_BE.service.pregunta.strategy.PreguntaLoaderStrategy;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PreguntaServiceImpl implements PreguntaService{

    private final PreguntaDao preguntaDao;
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
}