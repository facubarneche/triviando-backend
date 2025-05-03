package com.example.proyecto2025_BE.unittests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.example.proyecto2025_BE.model.Answer;
import com.example.proyecto2025_BE.model.Difficulty;
import com.example.proyecto2025_BE.model.LetterOption;
import com.example.proyecto2025_BE.model.Opcion;
import com.example.proyecto2025_BE.model.Pregunta;
import com.example.proyecto2025_BE.model.User;
import com.example.proyecto2025_BE.service.AnswerService;
import com.example.proyecto2025_BE.service.PreguntaService;
import com.example.proyecto2025_BE.service.UserService;

@DisplayName("Answer Service Test")
public class AnswerServiceTest {

	private static AnswerService answerService;
	private static UserService userServiceMock;
	private static PreguntaService preguntaServiceMock;
	private static Opcion correctOption;
	private static Answer.AnswerBuilder answerbuilder;
	
	@BeforeAll
	static void beforeAll() {
		userServiceMock = mock(UserService.class);
		preguntaServiceMock = mock(PreguntaService.class);
		answerService = new AnswerService(userServiceMock, preguntaServiceMock);
		
		User user = User.builder()
				.build();
		
		correctOption = Opcion.builder()
				.letter(LetterOption.A)
				.build();
		
		Pregunta pregunta = Pregunta.builder()
				.correctOption(correctOption)
				.difficulty(Difficulty.MEDIUM)
				.build();
		
		answerbuilder = Answer.builder()
				.questionId("e3r4g5th4nb3rg4t")
				.userId(12345L);
		
		when(userServiceMock.retrieve(anyLong())).thenReturn(user);
		when(preguntaServiceMock.getPreguntaById(anyString())).thenReturn(pregunta);
	}
	
	@BeforeEach
	void beforeEach() {
		
	}
	
	@Test
	@DisplayName("Success answer without latency penalty limit, without unsuccessful limit")
	void answerTest() {
		Answer answer = answerbuilder.millisecondsSpent(29999)
				.letterSelected(correctOption.getLetter())
				.build();
		
		BigDecimal score = answerService.answer(answer);
		
		assertEquals(BigDecimal.valueOf(16), score);
	}
	
	@Test
	@DisplayName("Success answer with latency penalty limit, without unsuccessful limit")
	void answerWithLatencyPenaltyLimitTest() {
		Answer answer = answerbuilder.millisecondsSpent(30001)
				.letterSelected(correctOption.getLetter())
				.build();
		
		BigDecimal score = answerService.answer(answer);
		
		assertEquals(BigDecimal.valueOf(1.6), score);
	}
	
	@Test
	@DisplayName("Unsuccess answer by unsuccessful limit")
	void answerWithUnsuccessfulLatencyLimitTest() {
		Answer answer = answerbuilder.millisecondsSpent(50001)
				.letterSelected(correctOption.getLetter())
				.build();
		
		BigDecimal score = answerService.answer(answer);
		
		assertEquals(BigDecimal.ZERO, score);
	}
	
	@Test
	@DisplayName("Unsuccess answer by incorrect option selected")
	void unsaccessAnswerTest() {
		Opcion incorrectOption = Opcion.builder()
				.letter(LetterOption.B)
				.build();
		
		Answer answer = answerbuilder.millisecondsSpent(29999)
				.letterSelected(incorrectOption.getLetter())
				.build();
		
		BigDecimal score = answerService.answer(answer);
		
		assertEquals(BigDecimal.ZERO, score);
	}
}
