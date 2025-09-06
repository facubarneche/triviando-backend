package com.example.proyecto2025_BE.service;

import com.example.proyecto2025_BE.dao.RespuestasDao;
import com.example.proyecto2025_BE.exceptions.ValidationException;
import com.example.proyecto2025_BE.model.Answer;
import com.example.proyecto2025_BE.model.FeedbackAnswer;
import com.example.proyecto2025_BE.model.Pregunta;
import com.example.proyecto2025_BE.model.User;
import com.example.proyecto2025_BE.service.command.ActualizarRachaCommand;
import com.example.proyecto2025_BE.service.command.UserInvoker;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class AnswerService {
	
	private final UserService userService;
	private final PreguntaService preguntaService;
	private final UserInvoker userInvoker;
	private final RespuestasDao answerRepository;

	@Transactional
	public FeedbackAnswer answer(Answer answer) {

		User user = userService.retrieve(answer.getUser().getId());

		Answer answered = answerRepository.findByUserIdAndQuestionId(user.getId(), answer.getQuestionId());

		if(answered != null) {
			throw new ValidationException("Esta pregunta ya fue respondida");
		}

		Pregunta question = preguntaService.getPreguntaById(answer.getQuestionId());
		answer.setResponseDate(LocalDate.now());
		answer.impactScore(question);
		user.add(answer);
		userInvoker.executeCommand(new ActualizarRachaCommand(user));
		userService.update(user);

		return FeedbackAnswer.builder()
				.score(answer.getScore())
				.explanation(question.getExplicacion())
				.errorReason(answer.getErrorReason())
				.correctOption(question.getCorrectOption())
				.build();
	}
}
