package com.example.proyecto2025_BE.model;

import java.util.List;

public record Question(String question, List<String>options, String correct) {
}
