package com.example.proyecto2025_BE.views.users;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserResponse4XX {

    @Schema(description = "Mensaje de error.")
    private String error;
}