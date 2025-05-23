package com.example.proyecto2025_BE.views.users.get;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@Schema(description = "Respuesta de un usuario")
public class GetUserResponse {

    @Schema(description = "ID del usuario", example = "12345")
    private long id;

    @Schema(description = "Nombre completo del usuario", example = "John Doe")
    private String fullName;

    @Schema(description = "Edad del usuario", example = "25")
    private int age;

    @Schema(description = "Correo electrónico del usuario", example = "t2k0f@example.com")
    private String email;

    @Schema(description = "Número de teléfono del usuario", example = "1234567890")
    private String phoneNumber;

    @Schema(description = "Fecha de registro del usuario", example = "2022-01-01")
    private LocalDate registerDate;
}
