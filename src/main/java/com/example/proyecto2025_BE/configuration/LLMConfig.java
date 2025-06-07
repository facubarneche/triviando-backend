package com.example.proyecto2025_BE.configuration;

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
import dev.langchain4j.model.openai.OpenAiChatRequestParameters;
import dev.langchain4j.model.openai.OpenAiStreamingChatModel;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.service.output.JsonSchemas;
import lombok.Setter;

@Setter
@Configuration
@ConfigurationProperties(prefix = "llm")
public class LLMConfig {
	private String apiKey;
	private String modelName;
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
		return OpenAiStreamingChatModel.builder()
			.apiKey(apiKey)
			.modelName(modelName)
			.logRequests(logRequests)
			.logResponses(logResponses)
			.timeout(LLM.TIMEOUT)
			.temperature(0.0)
			.strictJsonSchema(true)
			.defaultRequestParameters(OpenAiChatRequestParameters.builder()
					.responseFormat(responseFormat())
	                .build())
			.build();
	}
	
	private ResponseFormat responseFormat() {
		return ResponseFormat.builder()
			.jsonSchema(JsonSchemas.jsonSchemaFrom(QuestionList.class).get())
			.type(ResponseFormatType.JSON)
			.build();
	}
}
