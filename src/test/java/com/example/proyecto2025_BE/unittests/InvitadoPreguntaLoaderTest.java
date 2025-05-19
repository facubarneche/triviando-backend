package com.example.proyecto2025_BE.unittests;

import com.example.proyecto2025_BE.configuration.PreguntaProperties;
import com.example.proyecto2025_BE.dao.PreguntaDao;
import com.example.proyecto2025_BE.model.Difficulty;
import com.example.proyecto2025_BE.model.LetterOption;
import com.example.proyecto2025_BE.model.Option;
import com.example.proyecto2025_BE.model.Pregunta;
import com.example.proyecto2025_BE.service.pregunta.strategy.InvitadoPreguntaLoader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class InvitadoPreguntaLoaderTest {

    @Mock
    private PreguntaDao preguntaDao;

    @Mock
    private PreguntaProperties properties;

    private InvitadoPreguntaLoader invitadoLoader;

    private Pregunta pregunta1;
    private Pregunta pregunta2;
    private Pregunta pregunta3;

    @BeforeEach
    void setUp() {
        invitadoLoader = new InvitadoPreguntaLoader().init(preguntaDao, properties);

        // Configurar preguntas de ejemplo
        pregunta1 = Pregunta.builder()
                .id(UUID.randomUUID().toString())
                .topico("java")
                .enunciado("¿Qué es Java?")
                .explicacion("Un lenguaje de programación orientado a objetos.")
                .difficulty(Difficulty.LOW)
                .options(Arrays.asList(
                        Option.builder().text("Lenguaje de programación").letter(LetterOption.A).build(),
                        Option.builder().text("Sistema operativo").letter(LetterOption.B).build(),
                        Option.builder().text("Navegador web").letter(LetterOption.C).build(),
                        Option.builder().text("Base de datos").letter(LetterOption.D).build())
                )
                .correctOption(Option.builder().text("Lenguaje de programación").letter(LetterOption.A).build())
                .build();

        pregunta2 = Pregunta.builder()
                .id(UUID.randomUUID().toString())
                .topico("java")
                .enunciado("¿Qué es un objeto en Java?")
                .explicacion("Una instancia de una clase que tiene estado y comportamiento.")
                .difficulty(Difficulty.MEDIUM)
                .options(Arrays.asList(
                        Option.builder().text("Instancia de una clase").letter(LetterOption.A).build(),
                        Option.builder().text("Función").letter(LetterOption.B).build(),
                        Option.builder().text("Variable").letter(LetterOption.C).build(),
                        Option.builder().text("Paquete").letter(LetterOption.D).build())
                )
                .correctOption(Option.builder().text("Instancia de una clase").letter(LetterOption.A).build())
                .build();

        pregunta3 = Pregunta.builder()
                .id(UUID.randomUUID().toString())
                .topico("java")
                .enunciado("¿Qué es la herencia en Java?")
                .explicacion("Un mecanismo que permite que una clase adquiera propiedades de otra.")
                .difficulty(Difficulty.HIGH)
                .options(Arrays.asList(
                        Option.builder().text("Mecanismo de adquisición de propiedades").letter(LetterOption.A).build(),
                        Option.builder().text("Creación de objetos").letter(LetterOption.B).build(),
                        Option.builder().text("Manejo de excepciones").letter(LetterOption.C).build(),
                        Option.builder().text("Gestión de memoria").letter(LetterOption.D).build())
                )
                .correctOption(Option.builder().text("Mecanismo de adquisición de propiedades").letter(LetterOption.A).build())
                .build();
    }

    @Test
    void cargarPreguntasNoRespondidasConTopicoValidoTest() {
        // Arrange
        String topico = "java";
        List<Pregunta> preguntas = List.of(pregunta1, pregunta2, pregunta3);
        when(preguntaDao.findByTopico(topico)).thenReturn(Optional.of(preguntas));
        when(properties.getCantidad()).thenReturn(2);

        // Act
        List<Pregunta> result = invitadoLoader.cargarPreguntasNoRespondidas(topico);

        // Assert
        assertEquals(2, result.size());
        assertTrue(preguntas.containsAll(result));
        verify(preguntaDao).findByTopico(topico);
        verify(properties).getCantidad();
    }


    @Test
    void cargarPreguntasNoRespondidasConTopicoInvalidoTest() {
        // Arrange
        String topico = "python";
        when(preguntaDao.findByTopico(topico)).thenReturn(Optional.empty());

        // Act
        List<Pregunta> result = invitadoLoader.cargarPreguntasNoRespondidas(topico);

        // Assert
        assertTrue(result.isEmpty());
        verify(preguntaDao).findByTopico(topico);
    }

    @Test
    void cargarPreguntasNoRespondidasConTopicoValidoPeroVacioTest() {
        // Arrange
        String topico = "javascript";
        when(preguntaDao.findByTopico(topico)).thenReturn(Optional.of(Collections.emptyList()));

        // Act
        List<Pregunta> result = invitadoLoader.cargarPreguntasNoRespondidas(topico);

        // Assert
        assertTrue(result.isEmpty());
        verify(preguntaDao).findByTopico(topico);
    }
}