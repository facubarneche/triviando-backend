package com.example.proyecto2025_BE.controller;


import com.example.proyecto2025_BE.model.Pregunta;
import com.example.proyecto2025_BE.model.dto.PreguntaRequest;
import com.example.proyecto2025_BE.service.PreguntasService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/preguntas")
@CrossOrigin(origins = "*") // TODO: Cambiar luego por los dominios que permitiremos que consuman esta api
public class PreguntasController {

    @Autowired
    private PreguntasService preguntasService;

    @GetMapping
    public List<Pregunta> getPreguntas(@RequestParam(value = "topico", required = false) String topico) {
        if (topico == null) {
            return preguntasService.getAllPreguntas();
        } else {
            return preguntasService.getPreguntasByTopico(topico);
        }
    }

    @GetMapping("/{id}")
    public  Pregunta getPreguntaById(@PathVariable String id) {
        return preguntasService.getPreguntaById(id);
    }

    @PostMapping
    public ResponseEntity<?> createPregunta(@RequestBody @Valid PreguntaRequest preguntaRequest, BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }
        return ResponseEntity.ok(preguntasService.createPregunta(preguntaRequest));
    }
}
