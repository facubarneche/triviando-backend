package com.example.proyecto2025_BE.configuration;

import java.time.Duration;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.proyecto2025_BE.constants.LLM;
import com.example.proyecto2025_BE.model.dto.QuestionList;
import com.example.proyecto2025_BE.service.ModelCommunication;

import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.StreamingChatLanguageModel;
import dev.langchain4j.model.chat.request.ResponseFormat;
import dev.langchain4j.model.chat.request.ResponseFormatType;
import dev.langchain4j.model.ollama.OllamaStreamingChatModel;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.service.output.JsonSchemas;
import lombok.Data;

//@Data
@Configuration
//@ConfigurationProperties(prefix = "llm")
public class LLMConfig {
//	private String url;
	
	@Bean
	public ModelCommunication modelCommunication() {
		return AiServices.builder(ModelCommunication.class)
			.streamingChatLanguageModel(streamingChatLanguageModel())
			.chatMemory(MessageWindowChatMemory.withMaxMessages(10))
			.build();
	}
	
	private StreamingChatLanguageModel streamingChatLanguageModel() {
			return OllamaStreamingChatModel.builder()
	    	        .baseUrl("https://busy-smooth-sunbeam.ngrok-free.app")
	    	        .modelName(LLM.MODEL)
					.customHeaders(Map.of("Authorization", "Basic Z2VtbWEzOkxvc1BpYml0b3NEZUxhVW5zYW1NYW5kYW4u"))
					.logRequests(Boolean.TRUE)
					.logResponses(Boolean.TRUE)
	    	        .timeout(Duration.ofMinutes(1))
	    	        .responseFormat(responseFormat())
	    	        .topK(1)
	    	        .topP(0.1)
	    	        .temperature(0.0)
	    	        .build();
	}
	
	private ResponseFormat responseFormat() {
		return ResponseFormat.builder()
	        	.jsonSchema(JsonSchemas.jsonSchemaFrom(QuestionList.class).get())
	        	.type(ResponseFormatType.JSON)
	        	.build();
	}
}
