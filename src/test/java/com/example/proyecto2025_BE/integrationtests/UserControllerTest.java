package com.example.proyecto2025_BE.integrationtests;

import com.example.proyecto2025_BE.dao.RespuestasDao;
import com.example.proyecto2025_BE.dao.UserDao;
import com.example.proyecto2025_BE.model.Answer;
import com.example.proyecto2025_BE.model.User;
import com.example.proyecto2025_BE.model.dto.StatsResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DisplayName("User Controller Tests")
public class UserControllerTest {

    private static final Long EXISTENT_USER_ID = 1L;
    private static final Long INEXISTENT_USER_ID = 99L;

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserDao dao;
    @Autowired
    private ObjectMapper mapper;

    @MockitoBean
    private RespuestasDao respuestasDao;

    @BeforeEach
    void beforeEach() {
        dao.deleteAll();

        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
//
//        dao.save(user);
    }


    @Test
    @DisplayName("Cuando busco un user por id, y este existe, obtengo dicho recurso")
    void retrieveTest() throws Exception {
        User user = User.builder()
                .fullName("Pepe Palala")
                .build();
        dao.save(user);

        mockMvc.perform(MockMvcRequestBuilders.get("/users/{id}", EXISTENT_USER_ID))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.fullName").value("Pepe Palala"));
    }

    @Test
    @DisplayName("Cuando busco un user por id y este no es encontrado el sistema devuelve Not Found")
    void findByIdNotFoundTest() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.get("/users/{id}", INEXISTENT_USER_ID))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Se actualiza un user de manera exitosa")
    @DirtiesContext
    void updateTest() throws Exception {
        User user = User.builder()
                .fullName("Pepe Palala")
                .build();
        dao.save(user);
        Map<String, Object> map = new HashMap<>();
        map.put("fullName", "Emiliano");
        map.put("phoneNumber", "1234567890");
        String requestBody = mapper.writeValueAsString(map);

        mockMvc
                .perform(MockMvcRequestBuilders.patch("/users/{id}", user.getId(), requestBody)
                        .contentType("application/json")
                        .content(requestBody))
                .andExpect(content().contentType("application/json"))
                .andExpect(status().isOk());

        mockMvc
                .perform(MockMvcRequestBuilders.get("/users/{id}", user.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.phoneNumber").value("1234567890"));
    }

    @Test
    @DisplayName("Se crea un user de manera exitosa")
    void createTest() throws Exception {
        User userRequestDTO = User.builder()
                .username("PepePalala")
                .email("pepe.palala@gmail.com")
                .password("123456")
                .createdAt(LocalDateTime.now())
                .build();

        String requestBody = mapper.writeValueAsString(userRequestDTO);

        mockMvc.perform(MockMvcRequestBuilders.post("/users")
                        .contentType("application/json")
                        .content(requestBody))
                .andExpect(content().contentType("application/json"))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("Error al crear un usuario con email vacío")
    void createUserWithEmptyEmailTest() throws Exception {
        User invalidUser = User.builder()
                .fullName("Test User")
                .email("")
                .password("password123")
                .createdAt(LocalDateTime.now())
                .build();

        String requestBody = mapper.writeValueAsString(invalidUser);

        mockMvc.perform(MockMvcRequestBuilders.post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").exists());
    }

    @Test
    @DisplayName("Error al crear un usuario con email inválido")
    void createUserWithInvalidEmailFormatTest() throws Exception {
        User invalidUser = User.builder()
                .fullName("Test User")
                .email("invalid-email")
                .password("password123")
                .createdAt(LocalDateTime.now())
                .build();

        String requestBody = mapper.writeValueAsString(invalidUser);

        mockMvc.perform(MockMvcRequestBuilders.post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").exists());
    }

    @Test
    @DisplayName("Error al crear un usuario con nombre vacío")
    void createUserWithEmptyFullNameTest() throws Exception {
        User invalidUser = User.builder()
                .fullName("")
                .email("test@example.com")
                .password("password123")
                .createdAt(LocalDateTime.now())
                .build();

        String requestBody = mapper.writeValueAsString(invalidUser);

        mockMvc.perform(MockMvcRequestBuilders.post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").exists());
    }

    @Test
    @DisplayName("Error al crear un usuario con contraseña vacía")
    void createUserWithEmptyPasswordTest() throws Exception {
        User invalidUser = User.builder()
                .fullName("Test User")
                .email("test@example.com")
                .password("")
                .createdAt(LocalDateTime.now())
                .build();

        String requestBody = mapper.writeValueAsString(invalidUser);

        mockMvc.perform(MockMvcRequestBuilders.post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").exists());
    }

    @Test
    @DisplayName("Error al crear un usuario con campos nulos")
    void createUserWithNullFieldsTest() throws Exception {
        User invalidUser = User.builder()
                .fullName(null)
                .email(null)
                .password(null)
                .createdAt(LocalDateTime.now())
                .build();

        String requestBody = mapper.writeValueAsString(invalidUser);

        mockMvc.perform(MockMvcRequestBuilders.post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").exists());
    }

    @Test
    @DisplayName("Error al crear un usuario que ya existe")
    void createDuplicateUserTest() throws Exception {

        User user = User.builder()
                .fullName("Existing User")
                .email("existing@example.com")
                .password("password123")
                .createdAt(LocalDateTime.now())
                .build();

        dao.save(user);

        User duplicateUser = User.builder()
                .fullName("Another User")
                .email("existing@example.com")
                .password("differentpassword")
                .createdAt(LocalDateTime.now())
                .build();

        String requestBody = mapper.writeValueAsString(duplicateUser);

        mockMvc.perform(MockMvcRequestBuilders.post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").exists());
    }


    @Test
    @DisplayName("Estadisticas de un user que ha respondido preguntas")
    void estadisticasTest() throws Exception {
        when(respuestasDao.countByUserId(EXISTENT_USER_ID)).thenReturn(50);
        when(respuestasDao.countByUserIdAndErrorReasonIsNull(EXISTENT_USER_ID)).thenReturn(35);
        StatsResponse response = new StatsResponse(10, 35, 50);

        mockMvc.perform(MockMvcRequestBuilders.get("/users/statistics/{id}", EXISTENT_USER_ID))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().json(mapper.writeValueAsString(response)));
    }

    @Test
    @DisplayName("Estadisticas de un user que no ha respondido preguntas")
    void estadisticasInexistentUserTest() throws Exception {
        when(respuestasDao.countByUserId(EXISTENT_USER_ID)).thenReturn(0);
        when(respuestasDao.countByUserIdAndErrorReasonIsNull(EXISTENT_USER_ID)).thenReturn(0);
        StatsResponse response = new StatsResponse(0,0,0);

        mockMvc.perform(MockMvcRequestBuilders.get("/users/statistics/{id}", EXISTENT_USER_ID))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().json(mapper.writeValueAsString(response)));
    }

    @Test
    @DisplayName("Obtener el ranking de usuarios ordenados por score")
    void getUsersOrderedByScoreTest() throws Exception {
        // Preparar datos de prueba
        User user1 = User.builder().username("Usuario Alto Score").build();
        User user2 = User.builder().username("Usuario Medio Score").build();
        User user3 = User.builder().username("Usuario Bajo Score").build();

        Answer a1 = Answer.builder().score(new BigDecimal(100)).build();
        Answer a2 = Answer.builder().score(new BigDecimal(50)).build();
        Answer a3 = Answer.builder().score(new BigDecimal(25)).build();

        user1.setAnswers(List.of(a1));
        user2.setAnswers(List.of(a2));
        user3.setAnswers(List.of(a3));

        dao.save(user1);
        dao.save(user2);
        dao.save(user3);

        // Ejecutar y verificar
        mockMvc.perform(MockMvcRequestBuilders.get("/users/ranking"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content", hasSize(greaterThanOrEqualTo(3))))
                .andExpect(jsonPath("$.content[0].username").value("Usuario Alto Score"))
                .andExpect(jsonPath("$.content[0].score", closeTo(100.0, 0)))
                .andExpect(jsonPath("$.content[1].username").value("Usuario Medio Score"))
                .andExpect(jsonPath("$.content[1].score", closeTo(50.0, 0)))
                .andExpect(jsonPath("$.content[2].username").value("Usuario Bajo Score"))
                .andExpect(jsonPath("$.content[2].score", closeTo(25.0, 0)));
    }

    @Test
    @DisplayName("Obtener el ranking desde un usuario específico")
    void getUsersOrderedByScoreFromUserTest() throws Exception {
        User userHigh = User.builder().username("Usuario Alto").build();
        User userMid = User.builder().username("Usuario Medio").build();
        User userLow = User.builder().username("Usuario Bajo").build();

        Answer a1 = Answer.builder().score(new BigDecimal(500)).build();
        Answer a2 = Answer.builder().score(new BigDecimal(300)).build();
        Answer a3 = Answer.builder().score(new BigDecimal(100)).build();

        userHigh.setAnswers(List.of(a1));
        userMid.setAnswers(List.of(a2));
        userLow.setAnswers(List.of(a3));

        userHigh = dao.save(userHigh);
        userMid = dao.save(userMid);
        userLow = dao.save(userLow);

        Long midUserId = userMid.getId();

        mockMvc.perform(MockMvcRequestBuilders.get("/users/ranking/{userId}", midUserId)
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.content", hasSize(greaterThan(0))))
                // Verificar que los scores están ordenados correctamente
                .andExpect(jsonPath("$.content[0].score", closeTo(500.0, 0)))
                .andExpect(jsonPath("$.content[1].score", closeTo(300.0, 0)))
                .andExpect(jsonPath("$.content[2].score", closeTo(100.0, 0)))
                // Verificar que el usuario medio está en la posición correcta
                .andExpect(jsonPath("$.content[1].id").value(midUserId.intValue()))
                .andExpect(jsonPath("$.number").isNumber());
    }

    @Test
    @DisplayName("Obtener el ranking desde un usuario con mismo score que otro")
    void getUsersOrderedByScoreFromUserWithSameScoreTest() throws Exception {
        // Preparar datos de prueba - Usuarios con mismos scores pero diferentes IDs
        User user1 = User.builder().username("Usuario 1").build();
        User user2 = User.builder().username("Usuario 2").build();
        // Añadir usuarios adicionales para un ranking más completo
        User userHigh = User.builder().username("Usuario Alto").build();
        User userLow = User.builder().username("Usuario Bajo").build();
        Answer a1 = Answer.builder().score(new BigDecimal(300)).build();
        Answer a2 = Answer.builder().score(new BigDecimal(300)).build();
        Answer a3 = Answer.builder().score(new BigDecimal(500)).build();
        Answer a4 = Answer.builder().score(new BigDecimal(100)).build();

        user1.setAnswers(List.of(a1));
        user2.setAnswers(List.of(a2));
        userHigh.setAnswers(List.of(a3));
        userLow.setAnswers(List.of(a4));


        userHigh = dao.save(userHigh);
        user1 = dao.save(user1);
        user2 = dao.save(user2);
        userLow = dao.save(userLow);

        Long user1Id = user1.getId();
        Long user2Id = user2.getId();

        // Verificar que ambos tienen el mismo score
        assertEquals(user1.getScore().compareTo(user2.getScore()), 0);

        // Asumimos que user1 tiene ID menor que user2 por orden de inserción
        assertTrue(user1Id < user2Id, "Para esta prueba, user1 debe tener un ID menor que user2");

        // Prueba para el primer usuario
        mockMvc.perform(MockMvcRequestBuilders.get("/users/ranking/{userId}", user1Id)
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.content", hasSize(greaterThanOrEqualTo(4))))
                // Verificar que cuando hay mismo score, se ordenan por ID
                .andExpect(jsonPath("$.content[1].id").value(user1Id.intValue()))
                .andExpect(jsonPath("$.content[2].id").value(user2Id.intValue()))
                .andExpect(jsonPath("$.content[1].score", closeTo(300.0, 0)))
                .andExpect(jsonPath("$.content[2].score", closeTo(300.0, 0)))
                .andExpect(jsonPath("$.number").isNumber());

        // Prueba para el segundo usuario
        mockMvc.perform(MockMvcRequestBuilders.get("/users/ranking/{userId}", user2Id)
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.content", hasSize(greaterThanOrEqualTo(4))))
                .andExpect(jsonPath("$.content[*].id", hasItem(user2Id.intValue())))
                // El orden debe ser consistente con la prueba anterior
                .andExpect(jsonPath("$.content[1].id").value(user1Id.intValue()))
                .andExpect(jsonPath("$.content[2].id").value(user2Id.intValue()))
                .andExpect(jsonPath("$.number").isNumber());
    }

    @Test
    @DisplayName("Obtener el ranking desde un usuario inexistente debería retornar error 404")
    void getUsersOrderedByScoreFromInexistentUserTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/users/ranking/{userId}", INEXISTENT_USER_ID)
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isNotFound());
    }


    @Test
    @DisplayName("Obtener el ranking semanal de usuarios")
    void getWeeklyRankingTest() throws Exception {
        User userHighActive = User.builder().username("Usuario Alto Activo").build();
        User userMidActive = User.builder().username("Usuario Medio Activo").build();
        User userLowActive = User.builder().username("Usuario Bajo Activo").build();
        User userHighInactive = User.builder().username("Usuario Alto Inactivo").build();

        LocalDate currentDate = LocalDate.now();
        LocalDate previousWeekDate = currentDate.minusWeeks(1);

        userHighActive.setUltimaActividad(currentDate);
        userMidActive.setUltimaActividad(currentDate);
        userLowActive.setUltimaActividad(currentDate);
        userHighInactive.setUltimaActividad(previousWeekDate);

        Answer a1 = Answer.builder().score(new BigDecimal(100)).fechaRespuesta(currentDate).build();
        Answer a2 = Answer.builder().score(new BigDecimal(50)).fechaRespuesta(currentDate).build();
        Answer a3 = Answer.builder().score(new BigDecimal(25)).fechaRespuesta(currentDate).build();
        // respuesta hecha ene la semana pasada
        Answer a4 = Answer.builder().score(new BigDecimal(200)).fechaRespuesta(previousWeekDate).build();

        userHighActive.setAnswers(List.of(a1));
        userMidActive.setAnswers(List.of(a2));
        userLowActive.setAnswers(List.of(a3));
        userHighInactive.setAnswers(List.of(a4));

        dao.save(userHighActive);
        dao.save(userMidActive);
        dao.save(userLowActive);
        dao.save(userHighInactive);

        mockMvc.perform(MockMvcRequestBuilders.get("/users/ranking-semanal")
                .param("page", "0")
                .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                // solo deberian haber  3 usuarios (los activos esta semana)
                .andExpect(jsonPath("$.content", hasSize(3)))
                // vemos el orden correcto por puntuacion
                .andExpect(jsonPath("$.content[0].username").value("Usuario Alto Activo"))
                .andExpect(jsonPath("$.content[0].score", closeTo(100.0, 0)))
                .andExpect(jsonPath("$.content[1].username").value("Usuario Medio Activo"))
                .andExpect(jsonPath("$.content[1].score", closeTo(50.0, 0)))
                .andExpect(jsonPath("$.content[2].username").value("Usuario Bajo Activo"))
                .andExpect(jsonPath("$.content[2].score", closeTo(25.0, 0)))
                // usuario inactivo no esta en la respuesta
                .andExpect(jsonPath("$.content[*].username", not(hasItem("Usuario Alto Inactivo"))));
    }
    
    @Test
    @DisplayName("Cuando se loguea un usuario, y este esta registrado, obtengo dicho recurso")
    void loginTest() throws Exception {
    	User registeredUser = User.builder()
    			.email("pancho.rancho@gmail.com")
                .password("123456")
                .fullName("Pancho Rancho")
                .username("pancho_rancho_1746")
                .createdAt(LocalDateTime.now())
    			      .build();
    	
    	dao.save(registeredUser);
    	
    	
    	User requestBody = User.builder()
                .email("pancho.rancho@gmail.com")
                .password("123456")
                .createdAt(LocalDateTime.now())
                .build();

        String jsonBody = mapper.writeValueAsString(requestBody);

        mockMvc.perform(MockMvcRequestBuilders.post("/users/login")
                        .contentType("application/json")
                        .content(jsonBody))
                .andExpect(content().contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fullName").value("Pancho Rancho"))
                .andExpect(jsonPath("$.username").value("pancho_rancho_1746"));
    }
}
