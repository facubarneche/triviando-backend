package com.example.proyecto2025_BE.service;

import org.springframework.stereotype.Service;

import com.example.proyecto2025_BE.constants.LLM;
import com.example.proyecto2025_BE.exceptions.InternalServerErrorException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.service.TokenStream;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class LLMApiClient implements ChatLanguageModel {
	
	private static final String ERROR_MESSAGE = "Error in response deserialization";
	
	private final ModelCommunication assistant;
	private final ObjectMapper objectMapper;

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
							log.error(ERROR_MESSAGE);
							throw InternalServerErrorException.build(ERROR_MESSAGE);
						}
                    	sink.success(json);
                    })
                    .onError(sink::error)
                    .start();
        });
    }
}
