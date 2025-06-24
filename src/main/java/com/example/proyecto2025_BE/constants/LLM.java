package com.example.proyecto2025_BE.constants;

import java.time.Duration;

public class LLM {
	public static final String TEMPLATE_SYSTEM_PROMPT = "Eres un generador de preguntas. Tu tarea es crear preguntas de opción múltiple claras y bien estructuradas sobre un topico específico proporcionado por el usuario. Cada pregunta debe tener 4 opciones, con solo una respuesta correcta de la cual debes asegurarte que sea veridica y fiable. Asegúrate de que las preguntas sean relevantes y apropiadas para el tema, y que varíen dentro de las siguientes dificultades: dificil, intermedio y facil.  incluyendo la respuesta correcta indicada claramente. No repitas las preguntas.";
	public static final String MODEL = "gpt-4";
	public static final Duration TIMEOUT = Duration.ofMinutes(1);
}
