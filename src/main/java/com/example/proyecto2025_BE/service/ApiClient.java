package com.example.proyecto2025_BE.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.chat.response.ChatResponse;
import dev.langchain4j.model.output.TokenUsage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ApiClient implements ChatLanguageModel {
	
	private final HttpClient httpClient;
	private final ObjectMapper objectMapper;

    public String post() {
        try {
        	String modelName = "gemma3";
            String prompt = "Build me 5 questions about the stars";
            String requestBody = String.format("{\"model\": \"%s\", \"prompt\": \"%s\"}", modelName, prompt);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://busy-smooth-sunbeam.ngrok-free.app/api/generate"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .build();
            long init = System.currentTimeMillis();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            long end = System.currentTimeMillis();
            
            if (response.statusCode() != 200) {
            	log.error("Ollama API request failed: {}", response.statusCode() + " " + response.body());
            }
            
            log.info("Success response: {}", response.body());
            log.info("Request time spend in miliseconds: {}", end - init);
            
            return response.body();
        } catch (Exception e) {
        	log.error("Error calling Ollama API: {}", e.getMessage());
        	return e.getMessage();
        }
    }
}
