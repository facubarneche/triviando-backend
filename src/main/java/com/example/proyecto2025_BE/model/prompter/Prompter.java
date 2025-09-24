package com.example.proyecto2025_BE.model.prompter;

import com.example.proyecto2025_BE.service.IQuestion;
import com.example.proyecto2025_BE.service.ModelCommunication;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "promptType")
@JsonSubTypes({
        @JsonSubTypes.Type(value = QuestionPrompter.class, name = "questionPrompter"),
        @JsonSubTypes.Type(value = TopicPrompter.class, name = "topicPrompter")
})
public abstract class Prompter {

    private static final String TEMPLATE_PROMPT = "Generame %s preguntas cortas sobre %s";

    @NotBlank
    protected String topic;
    protected Long userId;
    protected IQuestion question;

    public String buildPrompt() {
        return String.format(TEMPLATE_PROMPT, questionsQuantity(), topic.toLowerCase());
    }

    protected abstract String questionsQuantity();

    public abstract void validatePrompt();

    public Prompter withService(IQuestion IQuestion) {
        this.question = IQuestion;
        return this;
    }

    public abstract String getEmoji(ModelCommunication assistant);

    public boolean existsTopic() {
        return question.existsByTopicAndUser(topic, userId);
    }

    public abstract void validateGeneration(Long userId);
}
