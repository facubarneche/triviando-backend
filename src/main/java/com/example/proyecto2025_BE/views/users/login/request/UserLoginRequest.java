package com.example.proyecto2025_BE.views.users.login.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Schema(description = "Representa la solicitud para iniciar sesión de un usuario existente.")
public class UserLoginRequest {

    @Schema(description = "Dirección de correo electrónico del usuario.", example = "juan.perez@example.com")
    private String email;

    @Schema(description = "Contraseña del usuario.", example = "PasswordSegura123!")
    private String password;
}