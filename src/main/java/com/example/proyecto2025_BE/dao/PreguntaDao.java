package com.example.proyecto2025_BE.dao;

import com.example.proyecto2025_BE.model.Pregunta;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PreguntaDao extends MongoRepository<Pregunta, String> {
    Optional<List<Pregunta>> findByTopico(String topico);
}


