package com.example.proyecto2025_BE.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "topicos")
@AllArgsConstructor
public class Topico {

    @Id
    private String id; // MongoDB uses String for IDs by default

    @Indexed(unique = true)
    private String topico;

    private int cantidadPreguntas;

    public void setTopico(String topico) {
        this.topico = topico != null ? topico.toLowerCase() : null;
    }
}
