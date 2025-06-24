package com.example.proyecto2025_BE.service;

import java.util.List;
import java.util.Map;

import com.example.proyecto2025_BE.model.dto.Topics;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.stereotype.Service;

import com.example.proyecto2025_BE.exceptions.InternalServerErrorException;
import com.example.proyecto2025_BE.model.Option;
import com.example.proyecto2025_BE.model.Pregunta;
import com.example.proyecto2025_BE.model.dto.llm.QuestionList;
import com.example.proyecto2025_BE.model.dto.llm.QuestionOption;
import com.example.proyecto2025_BE.model.prompter.Prompter;

import dev.langchain4j.model.chat.ChatLanguageModel;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class LLMApiClient implements ChatLanguageModel {
	
	private static final String ERROR_MESSAGE = "Error in LLM response deserialization";
	
	private final ModelCommunication assistant;
	private final PreguntaService preguntaService;

	@Transactional
	public List<Topics> generate(Prompter prompter) {
		prompter.withService(preguntaService)
			.validatePrompt();
		QuestionList response;

		String emoji = prompter.withService(preguntaService)
				.getEmoji(assistant);

		try {
			response = assistant.generateQuestions(prompter.buildPrompt());
		} catch (Exception e) {
			log.error(ERROR_MESSAGE);
			throw InternalServerErrorException.build(ERROR_MESSAGE);
		}

		return saveAndMappingResponse(response, prompter.getTopic(), emoji);
    }

	//TODO: evaluar la posibilidad de utilizar el strategy para realizar el parseo y el guardado de los datos para darle mas versatilidad a la integración
	private List<Topics> saveAndMappingResponse(QuestionList questionList, String topic, String emoji) {
		List<Pregunta> questions = questionList.questions().stream().map(question -> 
			Pregunta.builder()
				.topico(topic)
				.emoji(emoji)
				.enunciado(question.text())
				.options(buildIncorrectOptions(question.options()))
				.correctOption(buildCorrectOption(question.correctOption()))
				.explicacion(question.briefExplanationOfTheCorrectAnswer())
				.difficulty(question.difficulty())
				.build())
				.toList();
		preguntaService.saveAll(questions);

		//TODO: agregar emoji
		return preguntaService.contarPreguntasPorTopico();
	}

	private Option buildCorrectOption(QuestionOption correctOption) {
		return Option.builder()
				.text(correctOption.text())
				.letter(correctOption.letter())
				.build();
	}

	private List<Option> buildIncorrectOptions(List<QuestionOption> incorrectOptions) {
		return incorrectOptions.stream().map(option -> 
			Option.builder()
			.text(option.text())
			.letter(option.letter())
			.build())
			.toList();
	}
}
