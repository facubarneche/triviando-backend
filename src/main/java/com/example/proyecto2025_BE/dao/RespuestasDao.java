package com.example.proyecto2025_BE.dao;

import com.example.proyecto2025_BE.model.Respuesta;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RespuestasDao extends MongoRepository<Respuesta, String> {

    Integer countByUsuarioId(Long usuarioId);
    Integer countByUsuarioIdAndCorrectaTrue(Long usuarioId);
}
