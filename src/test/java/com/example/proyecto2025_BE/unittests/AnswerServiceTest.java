package com.example.proyecto2025_BE.unittests;

import com.example.proyecto2025_BE.model.*;
import com.example.proyecto2025_BE.service.AnswerService;
import com.example.proyecto2025_BE.service.PreguntaService;
import com.example.proyecto2025_BE.service.UserService;
import com.example.proyecto2025_BE.service.command.UserInvoker;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@DisplayName("Answer Service Test")
public class AnswerServiceTest {

	private static AnswerService answerService;
    private static Option correctOption;
	private static Answer.AnswerBuilder answerbuilder;

    @BeforeAll
	static void beforeAll() {
        UserService userServiceMock = mock(UserService.class);
        PreguntaService preguntaServiceMock = mock(PreguntaService.class);
        UserInvoker userInvoker = mock(UserInvoker.class);
		answerService = new AnswerService(userServiceMock, preguntaServiceMock, userInvoker);
		
		User user = User.builder()
				.id(1L)
				.build();
		
		correctOption = Option.builder()
				.letter(LetterOption.A)
				.build();
		
		Pregunta pregunta = Pregunta.builder()
				.correctOption(correctOption)
				.difficulty(Difficulty.MEDIUM)
				.build();
		
		answerbuilder = Answer.builder()
				.questionId("e3r4g5th4nb3rg4t")
				.user(user);
		
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
				.optionSelected(correctOption.getLetter())
				.build();
		
		FeedbackAnswer feedback = answerService.answer(answer);
		
		assertEquals(BigDecimal.valueOf(16), feedback.getScore());
	}
	
	@Test
	@DisplayName("Success answer with latency penalty limit, without unsuccessful limit")
	void answerWithLatencyPenaltyLimitTest() {
		Answer answer = answerbuilder.millisecondsSpent(30001)
				.optionSelected(correctOption.getLetter())
				.build();
		
		FeedbackAnswer feedback = answerService.answer(answer);
		
		assertEquals(BigDecimal.valueOf(1.6), feedback.getScore());
	}
	
	@Test
	@DisplayName("Unsuccess answer by unsuccessful limit")
	void answerWithUnsuccessfulLatencyLimitTest() {
		Answer answer = answerbuilder.millisecondsSpent(50001)
				.optionSelected(correctOption.getLetter())
				.build();
		
		FeedbackAnswer feedback = answerService.answer(answer);
		
		assertEquals(BigDecimal.ZERO, feedback.getScore());
	}
	
	@Test
	@DisplayName("Unsuccess answer by incorrect option selected")
	void unsaccessAnswerTest() {
		Option incorrectOption = Option.builder()
				.letter(LetterOption.B)
				.build();
		
		Answer answer = answerbuilder.millisecondsSpent(29999)
				.optionSelected(incorrectOption.getLetter())
				.build();
		
		FeedbackAnswer feedback = answerService.answer(answer);
		
		assertEquals(BigDecimal.ZERO, feedback.getScore());
	}
	
	@Test
	@DisplayName("Check feedback includes correct option")
	void feedbackIncludesCorrectOptionTest() {
		Answer answer = answerbuilder.millisecondsSpent(29999)
				.optionSelected(correctOption.getLetter())
				.build();
		
		FeedbackAnswer feedback = answerService.answer(answer);
		
		assertEquals(correctOption, feedback.getCorrectOption());
		assertEquals(LetterOption.A, feedback.getCorrectOption().getLetter());
	}
}