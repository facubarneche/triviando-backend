package com.example.proyecto2025_BE.model.prompter;

import com.example.proyecto2025_BE.service.ModelCommunication;
import com.fasterxml.jackson.annotation.JsonTypeName;

import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@JsonTypeName("questionPrompter")
@NoArgsConstructor
public class QuestionPrompter extends Prompter {

	@Override
	protected String questionsQuantity() {
		return "5";
	}

	@Override
	public void validatePrompt() {
		
	}

	@Override
	public String getEmoji(ModelCommunication assistant) {
		return super.preguntaService.getTopicFromQuestion(super.topic);
	}
}
