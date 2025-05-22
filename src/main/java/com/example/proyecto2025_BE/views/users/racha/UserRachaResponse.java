package com.example.proyecto2025_BE.views.users.racha;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@Schema(description = "Respuesta de la racha del usuario")
public class UserRachaResponse {

    @Schema(description = "Dias de racha", example = "5")
    private int rachaActual;
    @Schema(description = "Fecha de la ultima actividad", example = "2023-05-01")
    private LocalDate ultimaActividad;
}
