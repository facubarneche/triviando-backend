package com.example.proyecto2025_BE.model.prompter;

import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("questionPrompter")
public class QuestionPrompter extends Prompter {

	@Override
	protected String questionsQuantity() {
		return "5";
	}

	@Override
	public void validatePrompt() {
		
	}
}
