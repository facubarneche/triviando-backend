package com.example.proyecto2025_BE.model.prompter;

import com.example.proyecto2025_BE.exceptions.ConflictException;
import com.example.proyecto2025_BE.service.ModelCommunication;
import com.example.proyecto2025_BE.service.PreguntaService;
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
		if(super.existsTopic()) {
			throw ConflictException.build("Ya existe el tópico que se intenta crear");
		}
	}

	@Override
	public String getEmoji(ModelCommunication assistant) {
		String prompt =  String.format("Generame un emoji relacionado a el topico %s sin contenido extra (Solo el emoji)", super.topic.toLowerCase());
		return  assistant.generateEmoji(prompt);
	}
}
