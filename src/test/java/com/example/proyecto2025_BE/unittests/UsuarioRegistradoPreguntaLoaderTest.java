package com.example.proyecto2025_BE.unittests;

import com.example.proyecto2025_BE.configuration.PreguntaProperties;
import com.example.proyecto2025_BE.dao.PreguntaDao;
import com.example.proyecto2025_BE.model.*;
import com.example.proyecto2025_BE.service.UserService;
import com.example.proyecto2025_BE.service.pregunta.strategy.UsuarioRegistradoPreguntaLoader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UsuarioRegistradoPreguntaLoaderTest {

    @Mock
    private PreguntaDao preguntaDao;

    @Mock
    private PreguntaProperties properties;

    @Mock
    private UserService userService;

    private UsuarioRegistradoPreguntaLoader usuarioLoader;

    private User user;
    private Long userId;
    private Pregunta pregunta1;
    private Pregunta pregunta2;
    private Pregunta pregunta3;
    private Pregunta pregunta4;

    @BeforeEach
    void setUp() {
        userId = 1L;
        usuarioLoader = new UsuarioRegistradoPreguntaLoader(preguntaDao, properties, userService, userId);

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

        pregunta4 = Pregunta.builder()
                .id(UUID.randomUUID().toString())
                .topico("java")
                .enunciado("¿Qué es una interfaz en Java?")
                .explicacion("Un tipo de referencia similar a una clase que contiene solo constantes y métodos abstractos.")
                .difficulty(Difficulty.MEDIUM)
                .options(Arrays.asList(
                        Option.builder().text("Colección de métodos abstractos").letter(LetterOption.A).build(),
                        Option.builder().text("Clase concreta").letter(LetterOption.B).build(),
                        Option.builder().text("Tipo de variable").letter(LetterOption.C).build(),
                        Option.builder().text("Implementación de un objeto").letter(LetterOption.D).build())
                )
                .correctOption(Option.builder().text("Colección de métodos abstractos").letter(LetterOption.A).build())
                .build();

        // Configurar usuario
        user = new User();
        Answer answer1 = new Answer();
        answer1.setQuestionId(pregunta3.getId());
        user.setAnswers(List.of(answer1));
    }

    @Test
    void verificarFiltradoDePreguntasYaRespondidasPorUsuarioTest() {
        // Arrange
        String topico = "java";

        // Configurar un usuario con múltiples respuestas previas
        User userConRespuestas = new User();
        List<Answer> respuestasUsuario = new ArrayList<>();

        // El usuario ya respondió las preguntas 1 y 3
        Answer respuestaPregunta1 = new Answer();
        respuestaPregunta1.setQuestionId(pregunta1.getId());

        Answer respuestaPregunta3 = new Answer();
        respuestaPregunta3.setQuestionId(pregunta3.getId());

        respuestasUsuario.add(respuestaPregunta1);
        respuestasUsuario.add(respuestaPregunta3);
        userConRespuestas.setAnswers(respuestasUsuario);

        when(userService.retrieve(userId)).thenReturn(userConRespuestas);

        // IDs de preguntas ya respondidas por el usuario
        List<String> preguntasRespondidasIds = userConRespuestas.getAnswers().stream()
                .map(Answer::getQuestionId)
                .toList();

        // Verificamos que contiene los IDs de pregunta1 y pregunta3
        assertEquals(2, preguntasRespondidasIds.size());
        assertTrue(preguntasRespondidasIds.contains(pregunta1.getId()));
        assertTrue(preguntasRespondidasIds.contains(pregunta3.getId()));

        // Simulamos que el DAO devuelve solo las preguntas 2 y 4 (no respondidas)
        List<Pregunta> preguntasNoRespondidas = List.of(pregunta2, pregunta4);
        when(preguntaDao.findPreguntasNotAnsweredByUserIdAndTopico(preguntasRespondidasIds, topico))
                .thenReturn(preguntasNoRespondidas);

        when(properties.getCantidad()).thenReturn(2); // Queremos todas las disponibles

        // Act
        List<Pregunta> result = usuarioLoader.cargarPreguntasNoRespondidas(topico);

        // Assert
        assertEquals(2, result.size());

        // Verificar que las preguntas 1 y 3 (ya respondidas) NO están en el resultado
        assertFalse(result.stream().anyMatch(p -> p.getId().equals(pregunta1.getId())));
        assertFalse(result.stream().anyMatch(p -> p.getId().equals(pregunta3.getId())));

        // Verificar que solo están las preguntas 2 y 4
        assertTrue(result.stream().anyMatch(p -> p.getId().equals(pregunta2.getId())));
        assertTrue(result.stream().anyMatch(p -> p.getId().equals(pregunta4.getId())));

        // Verificar interacciones con el DAO
        verify(userService).retrieve(userId);
        verify(preguntaDao).findPreguntasNotAnsweredByUserIdAndTopico(preguntasRespondidasIds, topico);
    }

    @Test
    void verificarIntegracionCompletaDelFiltradoConUserServiceYDaoTest() {
        // Arrange
        String topico = "java";

        // Configuramos todas las preguntas disponibles en el sistema
        List<Pregunta> todasLasPreguntas = List.of(pregunta1, pregunta2, pregunta3, pregunta4);

        // El usuario ya respondió las preguntas 1 y 3
        User userConRespuestas = new User();
        List<Answer> respuestasUsuario = new ArrayList<>();

        Answer respuestaPregunta1 = new Answer();
        respuestaPregunta1.setQuestionId(pregunta1.getId());

        Answer respuestaPregunta3 = new Answer();
        respuestaPregunta3.setQuestionId(pregunta3.getId());

        respuestasUsuario.add(respuestaPregunta1);
        respuestasUsuario.add(respuestaPregunta3);
        userConRespuestas.setAnswers(respuestasUsuario);

        when(userService.retrieve(userId)).thenReturn(userConRespuestas);

        // IDs de preguntas ya respondidas
        List<String> preguntasRespondidasIds = userConRespuestas.getAnswers().stream()
                .map(Answer::getQuestionId)
                .toList();

        // Simulamos la respuesta del DAO con el filtrado de preguntas
        when(preguntaDao.findPreguntasNotAnsweredByUserIdAndTopico(preguntasRespondidasIds, topico))
                .thenAnswer(invocation -> {
                    List<String> idsExcluidos = invocation.getArgument(0);
                    return todasLasPreguntas.stream()
                            .filter(p -> !idsExcluidos.contains(p.getId()))
                            .filter(p -> topico == null || p.getTopico().equals(topico))
                            .collect(Collectors.toList());
                });

        when(properties.getCantidad()).thenReturn(2);

        // Act
        List<Pregunta> result = usuarioLoader.cargarPreguntasNoRespondidas(topico);

        // Assert
        assertEquals(2, result.size()); // Debido a que la propiedad cantidad es 2

        // Verificar que ninguna pregunta ya respondida está en el resultado
        for (Pregunta p : result) {
            assertFalse(preguntasRespondidasIds.contains(p.getId()));
        }

        // Verificar interacciones
        verify(userService).retrieve(userId);
        verify(preguntaDao).findPreguntasNotAnsweredByUserIdAndTopico(preguntasRespondidasIds, topico);
        verify(properties).getCantidad();
    }

    @Test
    void cargarPreguntasNoRespondidasConTopicoValidoYUsuarioSinRespuestasTest() {
        // Arrange
        String topico = "java";
        user.setAnswers(Collections.emptyList());
        when(userService.retrieve(userId)).thenReturn(user);
        List<String> preguntasRespondidasIds = user.getAnswers().stream()
                .map(Answer::getQuestionId)
                .toList();
        List<Pregunta> preguntasNoRespondidas = List.of(pregunta1, pregunta2, pregunta3);
        when(preguntaDao.findPreguntasNotAnsweredByUserIdAndTopico(preguntasRespondidasIds, topico))
                .thenReturn(preguntasNoRespondidas);
        when(properties.getCantidad()).thenReturn(2);

        // Act
        List<Pregunta> result = usuarioLoader.cargarPreguntasNoRespondidas(topico);

        // Assert
        assertEquals(2, result.size());
        assertTrue(preguntasNoRespondidas.containsAll(result));
        verify(userService).retrieve(userId);
        verify(preguntaDao).findPreguntasNotAnsweredByUserIdAndTopico(preguntasRespondidasIds, topico);
        verify(properties).getCantidad();
    }

    @Test
    void cargarPreguntasNoRespondidasConTopicoValidoPeroSinPreguntasDisponiblesTest() {
        // Arrange
        String topico = "java";
        when(userService.retrieve(userId)).thenReturn(user);
        List<String> preguntasRespondidasIds = user.getAnswers().stream()
                .map(Answer::getQuestionId)
                .toList();
        when(preguntaDao.findPreguntasNotAnsweredByUserIdAndTopico(preguntasRespondidasIds, topico))
                .thenReturn(Collections.emptyList());

        // Act
        List<Pregunta> result = usuarioLoader.cargarPreguntasNoRespondidas(topico);

        // Assert
        assertTrue(result.isEmpty());
        verify(userService).retrieve(userId);
        verify(preguntaDao).findPreguntasNotAnsweredByUserIdAndTopico(preguntasRespondidasIds, topico);
    }

}