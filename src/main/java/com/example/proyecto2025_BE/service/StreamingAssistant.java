package com.example.proyecto2025_BE.service;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.spring.AiService;
import reactor.core.publisher.Mono;

@AiService
public interface StreamingAssistant {

    @SystemMessage("You are a questions generator assistant")
    Mono<String> chat(String userMessage);
}
