package com.example.proyecto2025_BE.model;


import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "preguntas")
@Data
@Builder
public class Pregunta {

    @Id
    private String id;
    private String topico;
    private String enunciado;
    private List<Opcion> opciones;
}
