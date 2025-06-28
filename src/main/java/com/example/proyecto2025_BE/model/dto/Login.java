package com.example.proyecto2025_BE.model.dto;

import lombok.Builder;

@Builder
public record Login(
        String token,
        String username,
        String fullname,
        Long id
) { }
