package com.example.proyecto2025_BE.views.users.update.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@Schema(description = "Campos actualizables del usuario")
public class UpdateUserRequest {

    @Schema(description = "Nombre completo del usuario", example = "Juan Pérez")
    private String fullName;

    @Schema(description = "Nombre de usuario del usuario", example = "jperez")
    private String username;

    @Schema(description = "Correo electrónico del usuario", example = "juan@example.com")
    private String email;

    @Schema(description = "Contraseña del usuario", example = "Secreta123!")
    private String password;

    @Schema(description = "Fecha de nacimiento del usuario", example = "1990-05-21")
    private LocalDate birthDate;

    @Schema(description = "Número de teléfono del usuario", example = "+5491122334455")
    private String phoneNumber;
}