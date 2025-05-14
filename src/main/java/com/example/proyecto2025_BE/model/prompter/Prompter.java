package com.example.proyecto2025_BE.model.prompter;

import com.example.proyecto2025_BE.service.PreguntaService;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
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
	@NotBlank
	protected String promptContext;
	protected PreguntaService preguntaService;
	
	public String buildPrompt() {
		return String.format(TEMPLATE_PROMPT, questionsQuantity(), topic);
	}
	
	protected abstract String questionsQuantity();
	
	public abstract void validatePrompt();
	
	public Prompter withService(PreguntaService preguntaService) {
		this.preguntaService = preguntaService;
		return this;
	}
}
