package com.example.proyecto2025_BE.views.users.login.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Schema(description = "Representa la información del usuario devuelta después de un inicio de sesión exitoso.")
public class UserLoginResponse200 {

    @Schema(description = "ID único del usuario autenticado.", example = "12345")
    private Long id;

    @Schema(description = "Nombre completo del usuario autenticado.", example = "Juan Pérez")
    private String fullName;

    @Schema(description = "Nombre de usuario único del usuario autenticado.", example = "jperez")
    private String username;
}