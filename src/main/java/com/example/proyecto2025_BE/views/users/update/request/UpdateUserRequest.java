package com.example.proyecto2025_BE.views.users.update.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@Schema(description = "Campos actualizables del usuario")
public class UpdateUserRequest {
	
	@Schema(description = "ID del usuario", example = "12345")
    private long id;

	@Schema(description = "Nombre del usuario", example = "John")
    private String name;
    
    @Schema(description = "Apellido del usuario", example = "Doe")
    private String lastName;

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
    
    @Schema(description = "Código de área", example = "+54")
    private String countryCode;
    
    @Schema(description = "Password del usuario", example = "4Vwby%Q%$bYq#452V4WV2W")
    private String currentPassword;
}