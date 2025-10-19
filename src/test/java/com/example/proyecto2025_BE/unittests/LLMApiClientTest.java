package com.example.proyecto2025_BE.unittests;

import com.example.proyecto2025_BE.model.Account;
import com.example.proyecto2025_BE.model.Difficulty;
import com.example.proyecto2025_BE.model.LetterOption;
import com.example.proyecto2025_BE.model.dto.llm.Question;
import com.example.proyecto2025_BE.model.dto.llm.QuestionList;
import com.example.proyecto2025_BE.model.dto.llm.QuestionOption;
import com.example.proyecto2025_BE.model.prompter.Prompter;
import com.example.proyecto2025_BE.security.CustomUserDetails;
import com.example.proyecto2025_BE.service.IQuestion;
import com.example.proyecto2025_BE.service.LLMApiClientService;
import com.example.proyecto2025_BE.service.ModelCommunication;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@DisplayName("Ollama Api Client Tests")
public class LLMApiClientTest {

    private LLMApiClientService llmService;
    private ModelCommunication assistant;
    private IQuestion questionService;
    private QuestionList mockQuestions;

    @BeforeEach
    void setup() {
        questionService = mock(IQuestion.class);
        assistant = mock(ModelCommunication.class);
        llmService = new LLMApiClientService(assistant, questionService);

        QuestionOption correct = new QuestionOption("Lenguaje de programación", LetterOption.A);
        QuestionOption wrong = new QuestionOption("Sistema operativo", LetterOption.B);
        QuestionOption wrong2 = new QuestionOption("Una Fruta", LetterOption.C);
        QuestionOption wrong3 = new QuestionOption("Un Framework", LetterOption.D);

        Question question = new Question(
                "¿Qué es Java?",
                List.of(correct, wrong, wrong2, wrong3),
                correct,
                Difficulty.LOW,
                "Java es un lenguaje de programación multiplataforma."
        );

        mockQuestions = new QuestionList(List.of(question));
    }

    @Test
    void generateTopic() {
        Prompter prompter = mock(Prompter.class);
        when(prompter.withService(questionService)).thenReturn(prompter);
        when(prompter.buildPrompt()).thenReturn("mock prompt");
        when(prompter.getEmoji(any())).thenReturn("🔥");

        // Simular que no lanza excepción
        doNothing().when(prompter).validateGeneration(anyLong());
        doNothing().when(prompter).validatePrompt();

        // Mockear assistant y CustomUserDetails
        when(assistant.generateQuestions(anyString())).thenReturn(mockQuestions);
        CustomUserDetails userDetails = mock(CustomUserDetails.class);
        when(userDetails.getAccount()).thenReturn(Account.FREE);
        when(userDetails.getId()).thenReturn(1L);

        List<?> result = llmService.generate(prompter, userDetails);

        assertNotNull(result);
        verify(prompter, times(1)).validatePrompt();
        verify(prompter, times(1)).validateGeneration(1L);
        verify(prompter, times(1)).getEmoji(assistant);
        verify(questionService, times(1)).saveAll(anyList());
    }
}
