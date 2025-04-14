package com.example.proyecto2025_BE.dao;

import com.example.proyecto2025_BE.model.Topico;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TopicoDao extends MongoRepository<Topico, String> {}