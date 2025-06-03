package com.example.proyecto2025_BE.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.proyecto2025_BE.constants.Exceptions;
import com.example.proyecto2025_BE.model.Pregunta;
import com.example.proyecto2025_BE.model.prompter.Prompter;
import com.example.proyecto2025_BE.service.LLMApiClient;
import com.example.proyecto2025_BE.service.PreguntaService;
import com.example.proyecto2025_BE.views.questions.QuestionResponse200;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@CrossOrigin(origins = "*")
@RestController
@RequiredArgsConstructor
@RequestMapping("/preguntas")
@Tag(name = "Preguntas Controller", description = "API para la gestión de preguntas")
public class PreguntasController {

    private final PreguntaService preguntasService;
    private final LLMApiClient llmApiClient;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Obtiene todas las preguntas o las preguntas por tópico",
            description = "Si se proporciona un parámetro 'topico', devuelve las preguntas asociadas a ese tópico. " +
                    "De lo contrario, devuelve todas las preguntas." +
                    "Si se proporciona una userId valido, filtrará las preguntas ya respondidas por el mismo.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listado de preguntas obtenido exitosamente",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = QuestionResponse200.class))),
            @ApiResponse(responseCode = "404", description = Exceptions.NOT_FOUND, content = @Content)
    })
    public List<Pregunta> getPreguntas(@RequestParam(value = "userId", required = false) Long userId,
                                       @RequestParam(value = "topico", required = false) String topico) {
        return preguntasService.obtenerPreguntasNoRespondidasPorTopico(userId,topico);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Obtiene una pregunta por su ID",
            description = "Devuelve una pregunta específica mediante su identificador. Lanza una excepción si no existe.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pregunta encontrada",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = QuestionResponse200.class))),
            @ApiResponse(responseCode = "404", description = Exceptions.NOT_FOUND, content = @Content)
    })
    public Pregunta getPreguntaById(@PathVariable String id) {
        return preguntasService.getPreguntaById(id);
    }

    @GetMapping("/topicos")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Cuenta la cantidad de preguntas por tópico",
            description = "Devuelve una lista con la cantidad de preguntas agrupadas por tópico.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Conteo obtenido exitosamente",
                    content = @Content(mediaType = "application/json"))
    })
    public Map<String, Number> cantidadPreguntasPorTopico() {
        return preguntasService.contarPreguntasPorTopico();
    }
    
    @PostMapping("/generate")
    @Operation(summary = "Generar preguntas del LLM", description = "Generacion de preguntas según tópico",
	responses = {
	    @ApiResponse(responseCode = "200", description = "Preguntas generadas exitosamente",
	                  content = @Content(mediaType = "application/json")),
	})
    public Mono<List<Pregunta>> generate(@RequestBody Prompter prompter) {
        return llmApiClient.generate(prompter);
    }
}