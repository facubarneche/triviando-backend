package com.example.proyecto2025_BE.integrationtests;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.net.http.HttpClient;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.example.proyecto2025_BE.service.ApiClient;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DisplayName("Ollama Api Client Tests")
public class ApiClientTest {
	
	private ApiClient apiClient;
	private HttpClient httpClient;
	
	@Test
	void apiCallTest() {
		httpClient = HttpClient.newHttpClient();
		apiClient = new ApiClient(httpClient);
		
		String response = apiClient.post();
		
		assertNotNull(response);
	}
}
