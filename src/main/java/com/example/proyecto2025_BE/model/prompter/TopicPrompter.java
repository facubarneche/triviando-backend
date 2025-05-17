package com.example.proyecto2025_BE.model.prompter;

import com.example.proyecto2025_BE.exceptions.ConflictException;
import com.fasterxml.jackson.annotation.JsonTypeName;

import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@JsonTypeName("topicPrompter")
@NoArgsConstructor
public class TopicPrompter extends Prompter {

	@Override
	protected String questionsQuantity() {
		return "10";
	}

	@Override
	public void validatePrompt() {
		if(existsTopic()) {
			throw ConflictException.build("Ya existe el tópico que se intenta crear");
		}
	}
	
	private boolean existsTopic() {
		return super.preguntaService.existsByTopic(super.topic);
	}
}
