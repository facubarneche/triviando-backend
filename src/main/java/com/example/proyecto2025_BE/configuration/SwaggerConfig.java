package com.example.proyecto2025_BE.configuration;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(

        info = @Info(
                title = "Api Flashcards",
                description = "Aplicacion Flashcards",
                version = "1.0.0"
        ),
        servers = {
            @Server(
                    description = "DEV SERVER",
                    url = "http://localhost:8080/api/v1"
            )
        }
)
public class SwaggerConfig {

}
