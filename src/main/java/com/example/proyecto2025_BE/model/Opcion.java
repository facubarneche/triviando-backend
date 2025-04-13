package com.example.proyecto2025_BE.model;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Opcion {

    String opcion;
    boolean correcta;
    String explicacion;
}
