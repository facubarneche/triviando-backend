package com.example.proyecto2025_BE.model.dto.login;

import lombok.Builder;

@Builder
public record LoginRequestDTO(
        String username,
        String password
) {
}
