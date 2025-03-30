package com.example.proyecto2025_BE.model;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "preguntas")
@Data
public class Pregunta {

    @Id
    private String id;
    @Indexed(unique = true)
    private String enunciado;
    @Indexed
    private String topico;
    private List<String> opciones;
    private int respuestaCorrecta;
}
