package com.example.proyecto2025_BE.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "api.questions")
@Data
public class QuestionProperties {
    /**
     * Cantidad de preguntas que se generan por tópico
     */
    private int questionsPerTopic;

    /**
     * Máximo de tópicos que un usuario FREE puede crear por día
     */
    private int maxTopicsFreePerDay;

    /**
     * Máximo de preguntas que un usuario FREE puede crear por día
     */
    private int maxQuestionsFreePerDay;
}
