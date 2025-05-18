package com.example.proyecto2025_BE.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "api.preguntas")
@Data
public class PreguntaProperties {
    private int cantidad;
}
