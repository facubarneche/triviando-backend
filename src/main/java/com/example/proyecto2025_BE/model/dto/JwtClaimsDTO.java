package com.example.proyecto2025_BE.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record JwtClaimsDTO (
        //TODO: Pasar los mensajes a constants
        @NotNull(message = "El userId  no puede ser nulo")
        Long userId,
        String fullname,
        @NotBlank(message = "La cuenta no puede ser nula o vacia")
        String account
) {}
