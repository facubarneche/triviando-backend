package com.example.proyecto2025_BE.utils.racha;

public enum Racha implements RachaStrategy{
    MISMO_DIA {
        @Override
        public Integer calcularRacha(Integer rachaActual) {
            return rachaActual;
        }
    },
    DIA_CONSECUTIVO {
        @Override
        public Integer calcularRacha(Integer rachaActual) {
            return (rachaActual == null) ? 1 : rachaActual + 1;
        }
    },
    DIA_SALTEADO {
        @Override
        public Integer calcularRacha(Integer rachaActual) {
            return 1;
        }
    };
}
