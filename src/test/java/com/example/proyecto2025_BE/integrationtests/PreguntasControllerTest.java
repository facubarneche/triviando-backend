package com.example.proyecto2025_BE.integrationtests;

import com.example.proyecto2025_BE.configuration.PreguntasData;
import com.example.proyecto2025_BE.dao.FeedbackRepository;
import com.example.proyecto2025_BE.dao.PreguntaDao;
import com.example.proyecto2025_BE.dao.UserDao;
import com.example.proyecto2025_BE.model.Answer;
import com.example.proyecto2025_BE.model.Feedback;
import com.example.proyecto2025_BE.model.Pregunta;
import com.example.proyecto2025_BE.model.User;
import com.example.proyecto2025_BE.model.dto.FeedbackDTO;
import com.example.proyecto2025_BE.model.dto.Topics;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.util.*;

import static com.example.proyecto2025_BE.unittests.FeedbackServiceTest.createPositiveFeedbackDTO;
import static com.example.proyecto2025_BE.unittests.FeedbackServiceTest.createNegativeFeedbackDTO;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DisplayName("Preguntas Controller Test")
class PreguntasControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private PreguntaDao preguntaDao;
    @MockitoBean
    private UserDao userDao;
    @Autowired
    private ObjectMapper objectMapper;
    @MockitoBean
    private FeedbackRepository feedbackRepository;

    private final String emojiCafe = "\uD83D\uDC0D";

    @Test
    @DisplayName("Get preguntas by topico - topico existente")
    void getPreguntasByTopico_topicoExistente() throws Exception {
        String topicoExistente = PreguntasData.PREGUNTAS.getFirst().getTopico();
        List<Pregunta> preguntasTopicoExistente = PreguntasData.PREGUNTAS.stream()
                .filter(pregunta -> pregunta.getTopico().equals(topicoExistente))
                .toList();

        when(preguntaDao.findByTopico(topicoExistente)).thenReturn(Optional.of(preguntasTopicoExistente));

        mockMvc.perform(MockMvcRequestBuilders.get("/preguntas")
                        .param("topico", topicoExistente))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().json(objectMapper.writeValueAsString(preguntasTopicoExistente)));
    }


    @Test
    @DisplayName("Get preguntas by topico - user contesta preguntas")
    void getPreguntasByTopico_userContestaPreguntas() throws Exception {
        String topicoExistente = PreguntasData.PREGUNTAS.getFirst().getTopico();
        List<Pregunta> preguntasTopicoExistente = PreguntasData.PREGUNTAS.stream()
                .filter(pregunta -> pregunta.getTopico().equals(topicoExistente))
                .toList();

        when(preguntaDao.findByTopico(topicoExistente)).thenReturn(Optional.of(preguntasTopicoExistente));
        when(userDao.findById(anyLong())).thenReturn(Optional.of(User.builder()
                .id(1L)
                .answers(Collections.singletonList(Answer.builder()
                        .user(User.builder().id(1L).build())
                        .questionId(preguntasTopicoExistente.getFirst().getId())
                        .score(BigDecimal.valueOf(100.00))
                        .build()))
                .build()));

        List<Pregunta> resultadoEsperado = preguntasTopicoExistente.subList(1, preguntasTopicoExistente.size());
        when(preguntaDao.findPreguntasNotAnsweredByUserIdAndTopico(any(), any())).thenReturn(resultadoEsperado);


        mockMvc.perform(MockMvcRequestBuilders.get("/preguntas")
                        .param("topico", topicoExistente)
                        .param("userId", "1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().json(objectMapper.writeValueAsString(resultadoEsperado)));
    }

    @Test
    @DisplayName("Get preguntas by topico - topico no existente")
    void getPreguntasByTopico_topicoNoExistente() throws Exception {
        String topico = "Geografia";

        when(preguntaDao.findByTopico(topico)).thenReturn(Optional.empty());

        mockMvc.perform(MockMvcRequestBuilders.get("/preguntas")
                        .param("topico", topico))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().json("[]"));
    }

    @Test
    @DisplayName("Get preguntas by Id - Id existente")
    void getPreguntaById_IDValid() throws Exception {
        Pregunta pregunta = PreguntasData.PREGUNTAS.getFirst();

        when(preguntaDao.findById(pregunta.getId())).thenReturn(Optional.of(pregunta));

        mockMvc.perform(MockMvcRequestBuilders.get("/preguntas/{id}", pregunta.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().json(objectMapper.writeValueAsString(pregunta)));
    }

    @Test
    @DisplayName("Get Preguntas by Id - Id no existente")
    void getPreguntaById_IDInValid() throws Exception {
        String idInvalido = "ID_QUE_NO_EXISTE";

        when(preguntaDao.findById(idInvalido)).thenReturn(Optional.empty());

        mockMvc.perform(MockMvcRequestBuilders.get("/preguntas/{id}", idInvalido))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Get cantidad de preguntas por topico")
    void getCantidadPreguntasPorTopico() throws Exception {

        List<Map<String, Object>> resultadoDao = Arrays.asList(
                Map.of("_id", "java", "cantidadPreguntas", 5, "emoji", "☕"),
                Map.of("_id", "python", "cantidadPreguntas", 10,"emoji", emojiCafe),
                Map.of("_id", "javascript", "cantidadPreguntas", 7, "emoji", emojiCafe)
        );

        when(userDao.findById(anyLong())).thenReturn(Optional.of(User.builder().id(1L).build()));
        when(preguntaDao.contarPreguntasPorTopicoIncluyendoRespondidas(any())).thenReturn(resultadoDao);

        List<Topics> resultadoEsperado = List.of(
                Topics.builder()
                        .topic("java")
                        .size(5)
                        .emoji("☕")
                        .build(),
                Topics.builder()
                        .topic("python")
                        .size(10)
                        .emoji(emojiCafe)
                        .build(),
                Topics.builder()
                        .topic("javascript")
                        .size(7)
                        .emoji(emojiCafe)
                        .build()
        );

        mockMvc.perform(MockMvcRequestBuilders.get("/preguntas/topicos/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(resultadoEsperado)));
    }

    @Test
    @DisplayName("Crear feedback positivo")
    void crearFeedbackPositivo() throws Exception {
        feedbackRepository.deleteAll();
        FeedbackDTO positiveFeedbackDTO = createPositiveFeedbackDTO();
        String feedbackDtoJson = objectMapper.writeValueAsString(positiveFeedbackDTO);



        mockMvc.perform(MockMvcRequestBuilders.post("/preguntas/send-feedback")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(feedbackDtoJson))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("Crear feedback positivo")
    void crearFeedbacknegativo() throws Exception {
        feedbackRepository.deleteAll();

        FeedbackDTO negativeFeedbackDTO = createNegativeFeedbackDTO();
        String feedbackDtoJson = objectMapper.writeValueAsString(negativeFeedbackDTO);

        mockMvc.perform(MockMvcRequestBuilders.post("/preguntas/send-feedback")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(feedbackDtoJson))
                .andExpect(status().isCreated());
    }
}