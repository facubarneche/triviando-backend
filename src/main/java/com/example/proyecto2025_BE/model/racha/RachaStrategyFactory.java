package com.example.proyecto2025_BE.model.racha;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public final class RachaStrategyFactory {

    private RachaStrategyFactory(){}

    public static RachaStrategy getStrategy(LocalDate ultimaActividad, LocalDate today) {
        if (ultimaActividad == null) return Racha.DIA_SALTEADO;
        var days =(int) ChronoUnit.DAYS.between(ultimaActividad, today);
        return switch (days) {
            case 0 -> Racha.MISMO_DIA;
            case 1 -> Racha.DIA_CONSECUTIVO;
            default -> Racha.DIA_SALTEADO;
        };
    }
}

