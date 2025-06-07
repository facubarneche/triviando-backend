package com.example.proyecto2025_BE.views.users.register.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Schema(description = "Representa la información del usuario devuelta después de un registro exitoso.")
public class UserRegisterResponse {

    @Schema(description = "ID único del usuario", example = "12345")
    private long id;

    @Schema(description = "Nombre de usuario único", example = "jperez")
    private String username;
}
