package com.example.proyecto2025_BE.model.dto;

import java.util.List;

public record Question(String question, List<String>options, String correctAnswer) { }
