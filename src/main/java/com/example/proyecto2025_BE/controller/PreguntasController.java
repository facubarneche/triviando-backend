package com.example.proyecto2025_BE.controller;


import com.example.proyecto2025_BE.model.Pregunta;
import com.example.proyecto2025_BE.model.dto.PreguntaRequest;
import com.example.proyecto2025_BE.service.PreguntaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*") //
@RestController
@RequiredArgsConstructor
@RequestMapping("/preguntas")
public class PreguntasController {

    private final PreguntaService preguntasService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Pregunta> getPreguntas(@RequestParam(value = "topico", required = false) String topico) {
        if (topico == null) {
            return preguntasService.getAllPreguntas();
        } else {
            return preguntasService.getPreguntasByTopico(topico);
        }
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public  Pregunta getPreguntaById(@PathVariable String id) {
        return preguntasService.getPreguntaById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> createPregunta(@RequestBody @Valid PreguntaRequest preguntaRequest, BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }
        return ResponseEntity.ok(preguntasService.createPregunta(preguntaRequest));
    }

    @GetMapping("/cantidad-por-topico")
    @ResponseStatus(HttpStatus.OK)
    public List<Map<String,Integer>> cantidadPreguntasPorTopico(){
        return preguntasService.contarPreguntasPorTopico();
    }
}
