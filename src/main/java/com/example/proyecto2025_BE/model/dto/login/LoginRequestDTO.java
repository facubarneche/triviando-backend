package com.example.proyecto2025_BE.model.dto.login;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequestDTO {
    @NotBlank
    private String email;
    @NotBlank
    private String password;
}
