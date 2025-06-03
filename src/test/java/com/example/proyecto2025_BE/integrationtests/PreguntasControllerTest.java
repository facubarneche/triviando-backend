package com.example.proyecto2025_BE.integrationtests;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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

import com.example.proyecto2025_BE.configuration.PreguntasData;
import com.example.proyecto2025_BE.dao.PreguntaDao;
import com.example.proyecto2025_BE.model.Pregunta;
import com.example.proyecto2025_BE.model.dto.PreguntaRequest;
import com.fasterxml.jackson.databind.ObjectMapper;


@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DisplayName("Preguntas Controller Test")
class PreguntasControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private PreguntaDao preguntaDao;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Get preguntas by topico - topico existente")
    void getPreguntasByTopico_topicoExistente() throws Exception {
        String topico = "Historia";
        List<Pregunta> preguntasHistoria = PreguntasData.PREGUNTAS.stream()
                .filter(pregunta -> pregunta.getTopico().equals(topico))
                .toList();

        when(preguntaDao.findByTopico(topico)).thenReturn(Optional.of(preguntasHistoria));

        mockMvc.perform(MockMvcRequestBuilders.get("/preguntas")
                        .param("topico", topico))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().json(objectMapper.writeValueAsString(preguntasHistoria)));
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
    @DisplayName("Create a new pregunta - valid input")
    void createPregunta_validInput() throws Exception {
        PreguntaRequest preguntaRequest = PreguntaRequest.builder().topico("un_topico").build();
        String preguntaRequestJson = objectMapper.writeValueAsString(preguntaRequest);
        String expectedServiceResponse = "this action should create a new pregunta";


        mockMvc.perform(MockMvcRequestBuilders.post("/preguntas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(preguntaRequestJson))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedServiceResponse));
    }

    @Test
    @DisplayName("Create a new pregunta - invalid input")
    void createPregunta_invalidInput() throws Exception {
        PreguntaRequest preguntaRequest = PreguntaRequest.builder().build();
        String preguntaRequestJson = objectMapper.writeValueAsString(preguntaRequest);

        mockMvc.perform(MockMvcRequestBuilders.post("/preguntas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(preguntaRequestJson))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @DisplayName("Get cantidad de preguntas por topico")
    void getCantidadPreguntasPorTopico() throws Exception {

        List<Map<String, Object>> resultadoDao = Arrays.asList(
                Map.of("_id", "java", "cantidadPreguntas", 5),
                Map.of("_id", "python", "cantidadPreguntas", 10),
                Map.of("_id", "javascript", "cantidadPreguntas", 7)
        );

        when(preguntaDao.contarPreguntasPorTopico()).thenReturn(resultadoDao);

        Map<String, Number> resultadoEsperado = new HashMap<>();
        resultadoEsperado.put("java", 5);
        resultadoEsperado.put("python", 10);
        resultadoEsperado.put("javascript", 7);

        mockMvc.perform(MockMvcRequestBuilders.get("/preguntas/topicos"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(resultadoEsperado)));
    }
}