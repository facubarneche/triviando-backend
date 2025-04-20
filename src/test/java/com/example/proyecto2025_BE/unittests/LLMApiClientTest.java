package com.example.proyecto2025_BE.unittests;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.example.proyecto2025_BE.service.LLMApiClient;
import com.example.proyecto2025_BE.service.ModelCommunication;
import com.fasterxml.jackson.databind.ObjectMapper;

import dev.langchain4j.service.TokenStream;

@DisplayName("Ollama Api Client Tests")
public class LLMApiClientTest {
	
	private static ModelCommunication assistantMock;
	private static ObjectMapper objectMapperMock;
	private static TokenStream tokenStreamMock;
	private static LLMApiClient llmApiClient;
	
	@BeforeAll
	static void beforeAll() {
		objectMapperMock = mock(ObjectMapper.class);
		assistantMock = mock(ModelCommunication.class);
		tokenStreamMock = mock(TokenStream.class);
		llmApiClient = new LLMApiClient(assistantMock, objectMapperMock);
	}
	
	@Test
	void generateApiCallTest() throws Exception {
		when(assistantMock.chatWithModel(any())).thenReturn(tokenStreamMock);
		when(tokenStreamMock.onPartialResponse(any())).thenReturn(tokenStreamMock);
		when(tokenStreamMock.onCompleteResponse(any())).thenReturn(tokenStreamMock);
		when(tokenStreamMock.onError(any())).thenReturn(tokenStreamMock);
		doNothing().when(tokenStreamMock).start();
		
		assertNotNull(llmApiClient.generate("cars"));
	}
}
