package com.example.proyecto2025_BE.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.proyecto2025_BE.exceptions.InternalServerErrorException;
import com.example.proyecto2025_BE.model.Option;
import com.example.proyecto2025_BE.model.Pregunta;
import com.example.proyecto2025_BE.model.dto.llm.QuestionList;
import com.example.proyecto2025_BE.model.dto.llm.QuestionOption;
import com.example.proyecto2025_BE.model.prompter.Prompter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.service.TokenStream;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class LLMApiClient implements ChatLanguageModel {
	
	private static final String ERROR_MESSAGE = "Error in LLM response deserialization";
	
	private final ModelCommunication assistant;
	private final ObjectMapper objectMapper;
	private final PreguntaService preguntaService;

	@Transactional
	public Mono<List<Pregunta>> generate(Prompter prompter) {
		prompter.withService(preguntaService)
			.validatePrompt();
        TokenStream tokenStream = assistant.chatWithModel(prompter.buildPrompt());
        StringBuilder fullResponse = new StringBuilder();

        return Mono.<List<Pregunta>>create(sink -> {
            tokenStream.onPartialResponse(fullResponse::append)
                    .onCompleteResponse(response -> {
                    	QuestionList questionList = buildResponse(fullResponse);
                    	List<Pregunta> preguntas = saveAndMappingResponse(questionList, prompter.getTopic());
                    	sink.success(preguntas);
                    })
                    .onError(sink::error)
                    .start();
        });
    }
	
	private QuestionList buildResponse(StringBuilder fullResponse) {
		QuestionList questionList = null;
		try {
			questionList = objectMapper.readValue(fullResponse.toString(), QuestionList.class);
		} catch (JsonProcessingException e) {
			log.error(ERROR_MESSAGE);
			throw InternalServerErrorException.build(ERROR_MESSAGE);
		}
		
		return questionList;
	}
	
	private List<Pregunta> saveAndMappingResponse(QuestionList questionList, String topic) {
		List<Pregunta> questions = questionList.questions().stream().map(question -> 
			Pregunta.builder()
				.topico(topic)
				.emoji(question.emoji())
				.enunciado(question.text())
				.options(buildIncorrectOptions(question.options()))
				.correctOption(buildCorrectOption(question.correctOption()))
				.explicacion(question.briefExplanationOfTheCorrectAnswer())
				.difficulty(question.difficulty())
				.build())
				.toList();
		
		return preguntaService.saveAll(questions);
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
