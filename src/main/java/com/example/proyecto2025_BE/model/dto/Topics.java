package com.example.proyecto2025_BE.model.dto;

import lombok.Builder;

@Builder
public record Topics(
        String topic,
        Number size,
        String emoji
) { }
