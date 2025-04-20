package com.example.proyecto2025_BE.service;

import java.time.Duration;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.proyecto2025_BE.constants.LLM;
import com.example.proyecto2025_BE.model.dto.QuestionList;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.chat.StreamingChatLanguageModel;
import dev.langchain4j.model.chat.request.ResponseFormat;
import dev.langchain4j.model.chat.request.ResponseFormatType;
import dev.langchain4j.model.ollama.OllamaStreamingChatModel;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.service.TokenStream;
import dev.langchain4j.service.output.JsonSchemas;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class LLMApiClient implements ChatLanguageModel {
	
//	private final StreamingChatLanguageModel languageModel;
	private final ModelCommunication assistant;
	private final ObjectMapper objectMapper;

//	public LLMApiClient() {
//		this.languageModel = connectModel("https://busy-smooth-sunbeam.ngrok-free.app", LLM.MODEL);
//		ChatMemory chatMemory = MessageWindowChatMemory.withMaxMessages(10);
//		this.assistant = AiServices.builder(ModelCommunication.class)
//				.streamingChatLanguageModel(this.languageModel)
//				.chatMemory(chatMemory)
//				.build();
//	}


	public Mono<ObjectNode> generate(String topic) {
        String prompt = LLM.TEMPLATE_PROMPT + topic;
        TokenStream tokenStream = assistant.chatWithModel(prompt);
        StringBuilder fullResponse = new StringBuilder();

        return Mono.<ObjectNode>create(sink -> {
            tokenStream.onPartialResponse(fullResponse::append)
                    .onCompleteResponse(response -> {
                    	ObjectNode json = null;
						try {
							json = objectMapper.readValue(fullResponse.toString(), ObjectNode.class);
						} catch (JsonProcessingException e) {
							log.error("Error in response deserialization");
						}
                    	sink.success(json);
                    })
                    .onError(sink::error)
                    .start();
        });
    }

//    private StreamingChatLanguageModel connectModel(String modelUrl, String modelName) {
//        ResponseFormat responseFormat = ResponseFormat.builder()
//        	.jsonSchema(JsonSchemas.jsonSchemaFrom(QuestionList.class).get())
//        	.type(ResponseFormatType.JSON)
//        	.build();
//        
//		return OllamaStreamingChatModel.builder()
//    	        .baseUrl(modelUrl)
//    	        .modelName(modelName)
//				.customHeaders(Map.of("Authorization", "Basic Z2VtbWEzOkxvc1BpYml0b3NEZUxhVW5zYW1NYW5kYW4u"))
//				.logRequests(Boolean.TRUE)
//				.logResponses(Boolean.TRUE)
//    	        .timeout(Duration.ofMinutes(1))
//    	        .responseFormat(responseFormat)
//    	        .topK(1)
//    	        .topP(0.1)
//    	        .temperature(0.0)
//    	        .build();
//    }
}
