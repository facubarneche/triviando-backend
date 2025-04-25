package com.example.proyecto2025_BE.service;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import com.example.proyecto2025_BE.model.Answer;
import com.example.proyecto2025_BE.model.Pregunta;
import com.example.proyecto2025_BE.model.User;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AnswerService {
	
	private final UserService userService;
	private final PreguntaService preguntaService;
	
	@Transactional
	public BigDecimal answer(Answer answer) {
		User user = userService.retrieve(answer.getUserId());
		Pregunta question = preguntaService.getPreguntaById(answer.getQuestionId());
		
		BigDecimal score = answer.getScoreBy(question);
		user.add(answer);
		user.add(score);
		
		return score;
	}
	
}
