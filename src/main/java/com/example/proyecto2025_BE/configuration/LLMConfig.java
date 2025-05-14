package com.example.proyecto2025_BE.configuration;

import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.proyecto2025_BE.constants.LLM;
import com.example.proyecto2025_BE.model.dto.llm.QuestionList;
import com.example.proyecto2025_BE.service.ModelCommunication;

import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.StreamingChatLanguageModel;
import dev.langchain4j.model.chat.request.ResponseFormat;
import dev.langchain4j.model.chat.request.ResponseFormatType;
import dev.langchain4j.model.ollama.OllamaStreamingChatModel;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.service.output.JsonSchemas;
import lombok.Setter;

@Setter
@Configuration
@ConfigurationProperties(prefix = "llm")
public class LLMConfig {
	private String url;
	private String auth;
	private boolean logRequests;
	private boolean logResponses;
	
	@Bean
	public ModelCommunication modelCommunication() {
		return AiServices.builder(ModelCommunication.class)
			.streamingChatLanguageModel(streamingChatLanguageModel())
			.chatMemory(MessageWindowChatMemory.withMaxMessages(10))
			.systemMessageProvider(chatMemoryId -> LLM.TEMPLATE_SYSTEM_PROMPT)
			.build();
	}
	
	private StreamingChatLanguageModel streamingChatLanguageModel() {
			return OllamaStreamingChatModel.builder()
	    	        .baseUrl(url)
	    	        .modelName(LLM.MODEL)
					.customHeaders(Map.of("Authorization", auth))
					.logRequests(logRequests)
					.logResponses(logResponses)
	    	        .timeout(LLM.TIMEOUT)
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
