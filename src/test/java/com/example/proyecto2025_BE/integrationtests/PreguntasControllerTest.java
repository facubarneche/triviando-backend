package com.example.proyecto2025_BE.integrationtests;

import com.example.proyecto2025_BE.configuration.PreguntasData;
import com.example.proyecto2025_BE.repository.FeedbackRepository;
import com.example.proyecto2025_BE.repository.QuestionRepository;
import com.example.proyecto2025_BE.repository.UserRepository;
import com.example.proyecto2025_BE.model.Answer;
import com.example.proyecto2025_BE.model.Difficulty;
import com.example.proyecto2025_BE.model.Pregunta;
import com.example.proyecto2025_BE.model.User;
import com.example.proyecto2025_BE.model.dto.FeedbackDTO;
import com.example.proyecto2025_BE.model.dto.Topics;
import com.example.proyecto2025_BE.security.JwtUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.util.*;

import static com.example.proyecto2025_BE.unittests.FeedbackServiceTest.createPositiveFeedbackDTO;
import static com.example.proyecto2025_BE.unittests.FeedbackServiceTest.createNegativeFeedbackDTO;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DisplayName("Preguntas Controller Test")
class PreguntasControllerTest {

    @Autowired
    private JwtUtil jwtUtil;

    private static String jwtToken;

    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private QuestionRepository questionRepository;
    @MockitoBean
    private UserRepository userRepository;
    @Autowired
    private ObjectMapper objectMapper;
    @MockitoBean
    private FeedbackRepository feedbackRepository;

    private final String emojiCafe = "\uD83D\uDC0D";
    @Autowired
    private PasswordEncoder encoder;

    private User user;

    @BeforeEach
    public void setUp() {
        var encodedPass = encoder.encode("pelele");
        user = User.builder()
                .id(1L)
                .username("juanceto01")
                .password(encodedPass)
                .email("dsadsa@dsada.com")
                .build();
        jwtToken = jwtUtil.generateToken(user.getUsername());

        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(user));
    }

    @Test
    @DisplayName("Get preguntas by topico - topico existente")
    void getPreguntasByTopico_topicoExistente() throws Exception {
        String topicoExistente = PreguntasData.PREGUNTAS.getFirst().getTopico();
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(User.builder().id(1L).build()));
        List<Pregunta> preguntasTopicoExistente = PreguntasData.PREGUNTAS.stream()
                .filter(pregunta -> pregunta.getTopico().equals(topicoExistente))
                .toList();

        when(questionRepository.findPreguntasNotAnsweredByUserIdAndTopico(anyList(), any(String.class),any(Long.class))).thenReturn(preguntasTopicoExistente);


        mockMvc.perform(MockMvcRequestBuilders.get("/preguntas")
                        .param("userId", "1")
                        .param("topico", topicoExistente)
                        .header("Authorization", "Bearer " + jwtToken)
                )
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
        var user = userRepository.findByUsername("juanceto01").get();

        user.setAnswers(Collections.singletonList(Answer.builder()
                        .user(user)
                        .questionId(preguntasTopicoExistente.getFirst().getId())
                        .score(BigDecimal.valueOf(100.00))
                        .build()))
                ;
        userRepository.save(user);

        when(questionRepository.findByTopicoAndUserId(anyString(),anyLong())).thenReturn(preguntasTopicoExistente);
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(User.builder()
                .id(1L)
                .answers(Collections.singletonList(Answer.builder()
                        .user(User.builder().id(1L).build())
                        .questionId(preguntasTopicoExistente.getFirst().getId())
                        .score(BigDecimal.valueOf(100.00))
                        .build()))
                .build()));

        List<Pregunta> resultadoEsperado = preguntasTopicoExistente.subList(1, preguntasTopicoExistente.size());
        when(questionRepository.findPreguntasNotAnsweredByUserIdAndTopico(any(), any(),any())).thenReturn(resultadoEsperado);


        mockMvc.perform(MockMvcRequestBuilders.get("/preguntas")
                        .param("topico", topicoExistente)
                        .param("userId", user.getId().toString())
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().json(objectMapper.writeValueAsString(resultadoEsperado)));
    }

    @Test
    @DisplayName("Get preguntas by topico - topico no existente")
    void getPreguntasByTopico_topicoNoExistente() throws Exception {
        String topico = "Geografia";

        when(questionRepository.findPreguntasNotAnsweredByUserIdAndTopico(anyList(),anyString(),anyLong())).thenReturn(List.of());
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(User.builder().id(1L).build()));

        mockMvc.perform(MockMvcRequestBuilders.get("/preguntas")
                        .param("topico", topico)
                        .param("userId", "1")
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().json("[]"));
    }

    @Test
    @DisplayName("Get preguntas by Id - Id existente")
    void getPreguntaById_IDValid() throws Exception {
        Pregunta pregunta = PreguntasData.PREGUNTAS.getFirst();

        when(questionRepository.findById(pregunta.getId())).thenReturn(Optional.of(pregunta));

        mockMvc.perform(MockMvcRequestBuilders.get("/preguntas/{id}", pregunta.getId())
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().json(objectMapper.writeValueAsString(pregunta)));
    }

    @Test
    @DisplayName("Get Preguntas by Id - Id no existente")
    void getPreguntaById_IDInValid() throws Exception {
        String idInvalido = "ID_QUE_NO_EXISTE";

        when(questionRepository.findById(idInvalido)).thenReturn(Optional.empty());

        mockMvc.perform(MockMvcRequestBuilders.get("/preguntas/{id}", idInvalido)
                        .header("Authorization", "Bearer " + jwtToken))
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

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(User.builder().id(1L).build()));
        when(questionRepository.contarPreguntasPorTopicoIncluyendoRespondidas(any(),anyLong())).thenReturn(resultadoDao);

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

        mockMvc.perform(MockMvcRequestBuilders.get("/preguntas/topicos/{id}", 1L)
                        .header("Authorization", "Bearer " + jwtToken))
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
                        .content(feedbackDtoJson)
                        .header("Authorization", "Bearer " + jwtToken))
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
                        .content(feedbackDtoJson)
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("Usuario carga solo sus topicos")
    void testCargarTopicosPropios() throws Exception {
        Long userId = 1L;
        Long userId2 = 2L;
        String topico1 = "topico1";
        String topico2 = "topico2";
        Pregunta pregunta1User1 = crearPreguntaTest(userId, topico1);
        Pregunta pregunta2User1 = crearPreguntaTest(userId, topico2);
        Pregunta pregunta1User2 = crearPreguntaTest(userId2, topico2);
        Pregunta pregunta2User2 = crearPreguntaTest(userId2, topico1);
        List<Pregunta> preguntasUser1 = List.of(pregunta1User1,pregunta2User1);
        List<Pregunta> preguntasUser2 = List.of(pregunta1User2,pregunta2User2);

        when(userRepository.findById(userId)).thenReturn(Optional.of(User.builder().id(userId).build()));
        when(userRepository.findById(userId2)).thenReturn(Optional.of(User.builder().id(userId2).build()));
        when(questionRepository.findPreguntasNotAnsweredByUserIdAndTopico(List.of(),null,userId)).thenReturn(preguntasUser1);
        when(questionRepository.findPreguntasNotAnsweredByUserIdAndTopico(List.of(),null,userId2)).thenReturn(preguntasUser2);

        MvcResult resultUser1 = mockMvc.perform(MockMvcRequestBuilders.get("/preguntas")
                        .param("userId", String.valueOf(userId))
                        .header("Authorization", "Bearer " + jwtToken))
                        .andExpect(status().isOk())
                        .andReturn();

        List<Pregunta> list = objectMapper.readValue(resultUser1.getResponse().getContentAsString(), new TypeReference<List<Pregunta>>() {});
        assertTrue(list.stream().allMatch(p -> p.getUserId().equals(userId)));

        MvcResult resultUser2 = mockMvc.perform(MockMvcRequestBuilders.get("/preguntas")
                        .param("userId", String.valueOf(userId2))
                        .header("Authorization", "Bearer " + jwtToken))
                        .andExpect(status().isOk())
                        .andReturn();

        list = objectMapper.readValue(resultUser2.getResponse().getContentAsString(), new TypeReference<List<Pregunta>>() {});
        assertTrue(list.stream().allMatch(p -> p.getUserId().equals(userId2)));
    }

    private Pregunta crearPreguntaTest(Long userId, String topico) {

        return Pregunta.builder()
                .id(UUID.randomUUID().toString())
                .topico(topico)
                .userId(userId)
                .options(List.of())
                .explicacion("explicacion")
                .difficulty(Difficulty.MEDIUM)
                .build();
    }
}