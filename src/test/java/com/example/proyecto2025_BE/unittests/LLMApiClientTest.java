package com.example.proyecto2025_BE.unittests;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;

import com.example.proyecto2025_BE.model.dto.llm.QuestionList;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.example.proyecto2025_BE.model.prompter.QuestionPrompter;
import com.example.proyecto2025_BE.service.LLMApiClient;
import com.example.proyecto2025_BE.service.ModelCommunication;
import com.example.proyecto2025_BE.service.PreguntaService;

import dev.langchain4j.service.TokenStream;

@DisplayName("Ollama Api Client Tests")
public class LLMApiClientTest {
	
	private static ModelCommunication assistantMock;
	private static TokenStream tokenStreamMock;
	private static QuestionList questionListMock;
	private static LLMApiClient llmApiClient;
	private static PreguntaService preguntaServiceMock;
	
	@BeforeAll
	static void beforeAll() {
		assistantMock = mock(ModelCommunication.class);
		preguntaServiceMock = mock(PreguntaService.class);
		llmApiClient = new LLMApiClient(assistantMock, preguntaServiceMock);
	}
	
	@Test
	void generateApiCallTest() throws Exception {
		when(assistantMock.generateQuestions(any())).thenReturn(questionListMock);
		when(tokenStreamMock.onPartialResponse(any())).thenReturn(tokenStreamMock);
		when(tokenStreamMock.onCompleteResponse(any())).thenReturn(tokenStreamMock);
		when(tokenStreamMock.onError(any())).thenReturn(tokenStreamMock);
		when(preguntaServiceMock.saveAll(any())).thenReturn(List.of());
		doNothing().when(tokenStreamMock).start();
		
		assertNotNull(llmApiClient.generate(QuestionPrompter.builder()
				.topic("cars")
				.promptContext("Any context")
				.preguntaService(preguntaServiceMock)
				.build()));
	}
}
