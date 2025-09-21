package com.example.proyecto2025_BE.unittests;

import com.example.proyecto2025_BE.model.dto.llm.QuestionList;
import com.example.proyecto2025_BE.security.UserContext;
import com.example.proyecto2025_BE.service.IQuestion;
import com.example.proyecto2025_BE.service.LLMApiClientService;
import com.example.proyecto2025_BE.service.ModelCommunication;
import com.example.proyecto2025_BE.service.QuestionService;
import dev.langchain4j.service.TokenStream;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;

import static org.mockito.Mockito.mock;

@DisplayName("Ollama Api Client Tests")
public class LLMApiClientTest {

    private static ModelCommunication assistantMock;
    private static TokenStream tokenStreamMock;
    private static QuestionList questionListMock;
    private static LLMApiClientService llmApiClientService;
    private static IQuestion IQuestionMock;
    private static QuestionService questionService;
    private static UserContext userContextMock;

    @BeforeAll
    static void beforeAll() {
        assistantMock = mock(ModelCommunication.class);
        IQuestionMock = mock(IQuestion.class);
        userContextMock = mock(UserContext.class);

        llmApiClientService = new LLMApiClientService(assistantMock, IQuestionMock, questionService, userContextMock);
    }

//	@Test
//	void generateApiCallTest() throws Exception {
//		when(assistantMock.generateQuestions(any())).thenReturn(questionListMock);
//		when(tokenStreamMock.onPartialResponse(any())).thenReturn(tokenStreamMock);
//		when(tokenStreamMock.onCompleteResponse(any())).thenReturn(tokenStreamMock);
//		when(tokenStreamMock.onError(any())).thenReturn(tokenStreamMock);
//		when(preguntaServiceMock.saveAll(any())).thenReturn(List.of());
//		doNothing().when(tokenStreamMock).start();
//
//		assertNotNull(llmApiClient.generate(QuestionPrompter.builder()
//				.topic("cars")
//				.promptContext("Any context")
//				.preguntaService(preguntaServiceMock)
//				.build()));
//	}
}
