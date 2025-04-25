package com.example.proyecto2025_BE.model;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document(collection = "respuestas_usuario")
@Data
@AllArgsConstructor
public class Respuesta {

    @Id
    private String id;
    @Indexed
    private Long usuarioId;
    private String preguntaId;
    @Indexed
    private boolean correcta;
    private LocalDate fechaRespuesta;
}
