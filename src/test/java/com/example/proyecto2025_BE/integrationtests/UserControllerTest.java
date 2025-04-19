package com.example.proyecto2025_BE.integrationtests;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.proyecto2025_BE.model.dto.register.UserRequestDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.example.proyecto2025_BE.dao.UserDao;
import com.example.proyecto2025_BE.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;


import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

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
	private User user;
	
	@BeforeEach
	void beforeEach() {
		user = User.builder()
				.fullName("Pepe Palala")
				.build();
		
		dao.save(user);
		dao.save(User.builder().build());
		dao.save(User.builder().build());
	}
	
	@Test
	@DisplayName("Cuando busco un user por id, y este existe, obtengo dicho recurso")
	void retrieveTest() throws Exception {
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
		Map<String,Object> map = new HashMap<>();
		map.put("fullName","Emiliano");
		map.put("phoneNumber","1234567890");
		String requestBody = mapper.writeValueAsString(map);
		
		mockMvc
			.perform(MockMvcRequestBuilders.patch("/users/{id}", EXISTENT_USER_ID, requestBody)
					.contentType("application/json")
					.content(requestBody))
			.andExpect(content().contentType("application/json"))
			.andExpect(status().isOk());
		
		mockMvc
			.perform(MockMvcRequestBuilders.get("/users/{id}", EXISTENT_USER_ID))
			.andExpect(status().isOk())
			.andExpect(content().contentType("application/json"))
			.andExpect(jsonPath("$.phoneNumber").value("1234567890"));
	}
	
	@Test
	@DisplayName("Se crea un user de manera exitosa")
	void createTest() throws Exception {
		UserRequestDTO userRequestDTO = new UserRequestDTO();
		userRequestDTO.setFullName("Pepe Palala");
		userRequestDTO.setEmail("pepe.palala@gmail.com");
		userRequestDTO.setPassword("123456");
		userRequestDTO.setBirthday(LocalDate.of(1990, 1, 1));
		
		String requestBody = mapper.writeValueAsString(userRequestDTO);
		
		mockMvc.perform(MockMvcRequestBuilders.post("/users")
					.contentType("application/json")
					.content(requestBody))
			.andExpect(content().contentType("application/json"))
			.andExpect(status().isOk());
	}
}
