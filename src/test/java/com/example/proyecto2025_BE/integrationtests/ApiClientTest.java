package com.example.proyecto2025_BE.integrationtests;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.net.http.HttpClient;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.example.proyecto2025_BE.service.ApiClient;

import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.model.chat.response.ChatResponse;
import reactor.core.publisher.Flux;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DisplayName("Ollama Api Client Tests")
public class ApiClientTest {
	
	private ApiClient apiClient;
//	private HttpClient httpClient;
	
	@Test
	void customApiCallTest() {
//		httpClient = HttpClient.newHttpClient();
//		apiClient = new ApiClient(httpClient);
//		
//		String response = apiClient.post();
//		
//		assertNotNull(response);
		
		apiClient = new ApiClient();
		Flux<Object> resp = apiClient.post("motorcycle yamaha R3");
		
		System.out.println(resp.blockFirst());
	}
	
//	@Test
//	void libraryApiCallTest() {
//		httpClient = HttpClient.newHttpClient();
//		apiClient = new ApiClient(httpClient);
//		
//		ChatResponse chatResponse = apiClient.post("maths");
//		AiMessage aiMessage = chatResponse.aiMessage();
//		String response = aiMessage.text();
//		
//		
//		System.out.println(response);
//		
//		assertNotNull(response);
//	}
}
