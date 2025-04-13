package com.example.proyecto2025_BE.integrationtests;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.example.proyecto2025_BE.dao.UserDao;
import com.example.proyecto2025_BE.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;


import org.springframework.test.web.servlet.MockMvc;

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
	void updateTest() throws Exception {
		user.setId(EXISTENT_USER_ID);
		user.setPhoneNumber("1234567890");
		String requestBody = mapper.writeValueAsString(user);
		
		mockMvc
			.perform(MockMvcRequestBuilders.put("/users")
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
		User userToCreate = User.builder()
				.fullName("Pancho Rancho")
				.age(21)
				.email("panch.rancho@mail.com")
				.build();
		
		String requestBody = mapper.writeValueAsString(userToCreate);
		
		mockMvc.perform(MockMvcRequestBuilders.post("/users")
					.contentType("application/json")
					.content(requestBody))
			.andExpect(content().contentType("application/json"))
			.andExpect(status().isOk());
	}
}
