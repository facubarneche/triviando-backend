package com.example.proyecto2025_BE.model.racha;

public class DiaConsecutivoStrategy implements RachaStrategy{

    @Override
    public Integer calcularRacha(Integer rachaActual) {
        return (rachaActual == null) ? 1 : rachaActual + 1;
    }
}
