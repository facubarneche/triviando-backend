package com.example.proyecto2025_BE.views.users.register.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Schema(description = "Representa la solicitud para registrar un nuevo usuario en el sistema.")
public class UserRegisterRequest {

    @Schema(description = "Nick del usuario, como 'jperez'.", example = "jperez")
    private String username;

    @Schema(description = "Dirección de correo electrónico única del usuario.", example = "juan.perez@example.com")
    private String email;

    @Schema(description = "Contraseña para la cuenta del usuario. Debe tener al menos 8 caracteres, incluyendo mayúsculas, minúsculas, números y símbolos.", example = "PasswordSegura123!")
    private String password;
}
