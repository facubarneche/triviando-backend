package com.example.proyecto2025_BE.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.proyecto2025_BE.model.Answer;
import com.example.proyecto2025_BE.model.FeedbackAnswer;
import com.example.proyecto2025_BE.model.dto.login.LoginResponseDTO;
import com.example.proyecto2025_BE.service.AnswerService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/answers")
@CrossOrigin(origins = "*")	// TODO: Cambiar luego por los dominios que permitiremos que consuman esta api
@RequiredArgsConstructor
@Tag(name = "Answer Controller", description = "API para la gestión de respuestas")
public class AnswerController {
	
	private final AnswerService answerService;

	@PostMapping
	@Operation(summary = "Creacion de respuesta", description = "Suma puntaje a un usuario de una pregunta respondida",
    responses = {
        @ApiResponse(responseCode = "200", description = "Puntaje al usuario actualizado",
                     content = @Content(mediaType = "application/json", schema = @Schema(implementation = LoginResponseDTO.class))),
    })
    public ResponseEntity<FeedbackAnswer> answer(@RequestBody Answer answer) {
		FeedbackAnswer feedback = answerService.answer(answer);
		
		return ResponseEntity.ok(feedback);
    }
}
