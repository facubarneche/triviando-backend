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

    @Schema(description = "Nombre del usuario", example = "John")
    private String name;
    
    @Schema(description = "Apellido del usuario", example = "Doe")
    private String lastName;
    
    @Schema(description = "Username del usuario", example = "Johny_Doe")
    private String username;

    @Schema(description = "Correo electrónico del usuario", example = "t2k0f@example.com")
    private String email;

    @Schema(description = "Número de teléfono del usuario", example = "1234567890")
    private String phoneNumber;
    
    @Schema(description = "Código de área", example = "+54")
    private String countryCode;
    
    @Schema(description = "Fecha de nacimiento", example = "1991-01-28")
    private LocalDate birthDate;

    @Schema(description = "Fecha de registro del usuario", example = "2022-01-01")
    private LocalDate joinDate;
}
