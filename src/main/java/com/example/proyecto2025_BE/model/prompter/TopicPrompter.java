package com.example.proyecto2025_BE.model.prompter;

import com.example.proyecto2025_BE.exceptions.ConflictException;

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
