package com.example.proyecto2025_BE.configuration;

import com.example.proyecto2025_BE.constants.LLM;
import com.example.proyecto2025_BE.model.prompter.PrompterModel;
import com.example.proyecto2025_BE.service.ModelCommunication;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.Capability;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.service.AiServices;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Set;

@Setter
@Configuration
@ConfigurationProperties(prefix = "llm")
public class LLMConfig {
    private String apiKey;
    private PrompterModel modelName;
    private boolean logRequests;
    private boolean logResponses;

    @Bean
    public ModelCommunication modelCommunication() {
        return AiServices.builder(ModelCommunication.class)
                .chatLanguageModel(chatLanguageModel())
                .chatMemory(MessageWindowChatMemory.withMaxMessages(10))
                .systemMessageProvider(chatMemoryId -> LLM.TEMPLATE_SYSTEM_PROMPT)
                .build();
    }

    private OpenAiChatModel chatLanguageModel() {
        return OpenAiChatModel.builder()
                .apiKey(apiKey)
                .modelName(modelName.getValue())
                .logRequests(logRequests)
                .logResponses(logResponses)
                .timeout(LLM.TIMEOUT)
                .temperature(0.0)
                .supportedCapabilities(Set.of(Capability.RESPONSE_FORMAT_JSON_SCHEMA))
                .strictJsonSchema(true)
                .build();
    }
}
