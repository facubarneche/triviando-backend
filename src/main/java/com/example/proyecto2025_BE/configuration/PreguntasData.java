package com.example.proyecto2025_BE.configuration;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.example.proyecto2025_BE.model.Difficulty;
import com.example.proyecto2025_BE.model.LetterOption;
import com.example.proyecto2025_BE.model.Option;
import com.example.proyecto2025_BE.model.Pregunta;

import lombok.RequiredArgsConstructor;

public class PreguntasData {

    public static final List<Pregunta> PREGUNTAS = Arrays.asList(
            Pregunta.builder()
                    .id(UUID.randomUUID().toString())
                    .topico("Arquitectura de Microservicios")
                    .emoji("📐")
                    .enunciado("¿Cuál es una ventaja clave de la arquitectura de microservicios?")
                    .explicacion("La escalabilidad independiente de los servicios permite optimizar recursos.")
                    .difficulty(Difficulty.MEDIUM)
                    .options(Arrays.asList(
                            Option.builder().text("Monoliticidad").letter(LetterOption.A).build(),
                            Option.builder().text("Mayor acoplamiento").letter(LetterOption.B).build(),
                            Option.builder().text("Escalabilidad independiente").letter(LetterOption.C).build(),
                            Option.builder().text("Implementaciones más complejas").letter(LetterOption.D).build())
                    )
                    .correctOption(Option.builder().text("Escalabilidad independiente").letter(LetterOption.C).build())
                    .build(),

            Pregunta.builder()
                    .id(UUID.randomUUID().toString())
                    .topico("Arquitectura de Microservicios")
                    .emoji("📐")
                    .enunciado("¿Qué patrón se utiliza comúnmente para la comunicación síncrona entre microservicios?")
                    .explicacion("RESTful APIs utilizando HTTP es un estándar para la comunicación síncrona.")
                    .difficulty(Difficulty.MEDIUM)
                    .options(Arrays.asList(
                            Option.builder().text("Message Queue").letter(LetterOption.A).build(),
                            Option.builder().text("RESTful APIs").letter(LetterOption.B).build(),
                            Option.builder().text("Event Sourcing").letter(LetterOption.C).build(),
                            Option.builder().text("Circuit Breaker").letter(LetterOption.D).build())
                    )
                    .correctOption(Option.builder().text("RESTful APIs").letter(LetterOption.B).build())
                    .build(),

            Pregunta.builder()
                    .id(UUID.randomUUID().toString())
                    .topico("Arquitectura de Microservicios")
                    .emoji("📐")
                    .enunciado("¿Cuál es el propósito del patrón 'Circuit Breaker' en microservicios?")
                    .explicacion("Evitar fallos en cascada al detener las solicitudes a servicios no disponibles.")
                    .difficulty(Difficulty.MEDIUM)
                    .options(Arrays.asList(
                            Option.builder().text("Balancear la carga").letter(LetterOption.A).build(),
                            Option.builder().text("Autenticar usuarios").letter(LetterOption.B).build(),
                            Option.builder().text("Registrar eventos").letter(LetterOption.C).build(),
                            Option.builder().text("Evitar fallos en cascada").letter(LetterOption.D).build())
                    )
                    .correctOption(Option.builder().text("Evitar fallos en cascada").letter(LetterOption.D).build())
                    .build(),

            Pregunta.builder()
                    .id(UUID.randomUUID().toString())
                    .topico("Arquitectura de Microservicios")
                    .emoji("📐")
                    .enunciado("¿Qué desafío introduce la arquitectura de microservicios en comparación con una monolítica?")
                    .explicacion("La gestión de la complejidad de la red y la comunicación entre servicios es un desafío.")
                    .difficulty(Difficulty.HIGH)
                    .options(Arrays.asList(
                            Option.builder().text("Menor necesidad de pruebas").letter(LetterOption.A).build(),
                            Option.builder().text("Implementación más sencilla").letter(LetterOption.B).build(),
                            Option.builder().text("Gestión de la complejidad de la red").letter(LetterOption.C).build(),
                            Option.builder().text("Mayor consistencia de datos").letter(LetterOption.D).build())
                    )
                    .correctOption(Option.builder().text("Gestión de la complejidad de la red").letter(LetterOption.C).build())
                    .build(),

            Pregunta.builder()
                    .id(UUID.randomUUID().toString())
                    .topico("Arquitectura de Microservicios")
                    .emoji("📐")
                    .enunciado("¿Qué rol juega un 'API Gateway' en una arquitectura de microservicios?")
                    .explicacion("Actúa como un punto de entrada único para todos los clientes, simplificando el acceso a los servicios.")
                    .difficulty(Difficulty.MEDIUM)
                    .options(Arrays.asList(
                            Option.builder().text("Implementar la lógica de negocio").letter(LetterOption.A).build(),
                            Option.builder().text("Gestionar la configuración de los servicios").letter(LetterOption.B).build(),
                            Option.builder().text("Actuar como un punto de entrada único").letter(LetterOption.C).build(),
                            Option.builder().text("Almacenar datos persistentes").letter(LetterOption.D).build())
                            
                    )
                    .correctOption(Option.builder().text("Actuar como un punto de entrada único").letter(LetterOption.C).build())
                    .build(),


            Pregunta.builder()
                    .id(UUID.randomUUID().toString())
                    .topico("Bases de Datos NoSQL")
                    .emoji("🗂️")
                    .enunciado("¿Cuál de las siguientes NO es una categoría principal de bases de datos NoSQL?")
                    .explicacion("Las bases de datos relacionales (SQL) no pertenecen a las categorías NoSQL.")
                    .difficulty(Difficulty.LOW)
                    .options(Arrays.asList(
                            Option.builder().text("Relacional").letter(LetterOption.A).build(),
                            Option.builder().text("Documento").letter(LetterOption.B).build(),
                            Option.builder().text("Clave-Valor").letter(LetterOption.C).build(),
                            Option.builder().text("Grafo").letter(LetterOption.D).build())
                    )
                    .correctOption(Option.builder().text("Relacional").letter(LetterOption.A).build())
                    .build(),

            Pregunta.builder()
                    .id(UUID.randomUUID().toString())
                    .topico("Bases de Datos NoSQL")
                    .emoji("🗂️")
                    .enunciado("¿Qué tipo de base de datos NoSQL es ideal para almacenar datos con relaciones complejas?")
                    .explicacion("Las bases de datos de grafo están diseñadas para modelar y consultar relaciones.")
                    .difficulty(Difficulty.MEDIUM)
                    .options(Arrays.asList(
                            Option.builder().text("Clave-Valor").letter(LetterOption.A).build(),
                            Option.builder().text("Documento").letter(LetterOption.B).build(),
                            Option.builder().text("Columna-Familiar").letter(LetterOption.C).build(),
                            Option.builder().text("Grafo").letter(LetterOption.D).build())
                    )
                    .correctOption(Option.builder().text("Grafo").letter(LetterOption.D).build())
                    .build(),

            Pregunta.builder()
                    .id(UUID.randomUUID().toString())
                    .topico("Bases de Datos NoSQL")
                    .emoji("🗂️")
                    .enunciado("¿Cuál es una característica clave de las bases de datos de documentos como MongoDB?")
                    .explicacion("Almacenan datos en estructuras flexibles tipo JSON o BSON.")
                    .difficulty(Difficulty.LOW)
                    .options(Arrays.asList(
                            Option.builder().text("Esquema rígido").letter(LetterOption.A).build(),
                            Option.builder().text("Transacciones ACID estrictas").letter(LetterOption.B).build(),
                            Option.builder().text("Almacenamiento basado en filas").letter(LetterOption.C).build(),
                            Option.builder().text("Esquema flexible").letter(LetterOption.D).build())
                    )
                    .correctOption(Option.builder().text("Esquema flexible").letter(LetterOption.D).build())
                    .build(),

            Pregunta.builder()
                    .id(UUID.randomUUID().toString())
                    .topico("Bases de Datos NoSQL")
                    .emoji("🗂️")
                    .enunciado("¿Qué significa el acrónimo CAP en el contexto de las bases de datos distribuidas?")
                    .explicacion("Consistencia, Disponibilidad y Tolerancia a Particiones.")
                    .difficulty(Difficulty.HIGH)
                    .options(Arrays.asList(
                            Option.builder().text("Consistencia, Atomicidad, Persistencia").letter(LetterOption.A).build(),
                            Option.builder().text("Consistencia, Disponibilidad, Persistencia").letter(LetterOption.B).build(),
                            Option.builder().text("Consistencia, Disponibilidad, Tolerancia a Particiones").letter(LetterOption.C).build(),
                            Option.builder().text("Atomicidad, Consistencia, Aislamiento, Durabilidad").letter(LetterOption.D).build())
                    )
                    .correctOption(Option.builder().text("Consistencia, Disponibilidad, Tolerancia a Particiones").letter(LetterOption.C).build())
                    .build(),

            Pregunta.builder()
                    .id(UUID.randomUUID().toString())
                    .topico("Bases de Datos NoSQL")
                    .emoji("🗂️")
                    .enunciado("¿Qué tipo de base de datos NoSQL se optimiza para consultas de series de tiempo?")
                    .explicacion("Las bases de datos de series de tiempo están diseñadas para manejar datos secuenciales con marcas de tiempo.")
                    .difficulty(Difficulty.MEDIUM)
                    .options(Arrays.asList(
                            Option.builder().text("Clave-Valor").letter(LetterOption.A).build(),
                            Option.builder().text("Columna-Familiar").letter(LetterOption.B).build(),
                            Option.builder().text("Documento").letter(LetterOption.C).build(),
                            Option.builder().text("Series de Tiempo").letter(LetterOption.D).build())
                    )
                    .correctOption(Option.builder().text("Series de Tiempo").letter(LetterOption.B).build())
                    .build()
            );
}
