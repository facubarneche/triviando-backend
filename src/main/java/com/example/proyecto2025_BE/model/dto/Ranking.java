package com.example.proyecto2025_BE.model.dto;

public record Ranking(
        Long id,
        String username,
        Double score,
        Integer position) {
}