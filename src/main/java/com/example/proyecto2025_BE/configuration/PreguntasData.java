package com.example.proyecto2025_BE.configuration;

import com.example.proyecto2025_BE.model.Difficulty;
import com.example.proyecto2025_BE.model.LetterOption;
import com.example.proyecto2025_BE.model.Option;
import com.example.proyecto2025_BE.model.Pregunta;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class PreguntasData {

    public static final List<Pregunta> PREGUNTAS = Arrays.asList(
            Pregunta.builder()
                    .id(UUID.randomUUID().toString())
                    .userId(1L)
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
                    .userId(1L)
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
                    .userId(1L)
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
                    .userId(1L)
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
                    .userId(1L)
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
                    .userId(1L)
                    .topico("Java")
                    .emoji("☕")
                    .enunciado("¿Qué significa JVM en el ecosistema de Java?")
                    .explicacion("La Java Virtual Machine ejecuta el bytecode compilado y lo convierte en instrucciones para el sistema operativo.")
                    .difficulty(Difficulty.LOW)
                    .options(Arrays.asList(
                            Option.builder().text("Java Variable Manager").letter(LetterOption.A).build(),
                            Option.builder().text("Java Virtual Machine").letter(LetterOption.B).build(),
                            Option.builder().text("Java Visual Module").letter(LetterOption.C).build(),
                            Option.builder().text("Java Value Mapper").letter(LetterOption.D).build())
                    )
                    .correctOption(Option.builder().text("Java Virtual Machine").letter(LetterOption.B).build())
                    .build(),

            Pregunta.builder()
                    .id(UUID.randomUUID().toString())
                    .userId(1L)
                    .topico("Java")
                    .emoji("☕")
                    .enunciado("¿Cuál es la diferencia principal entre JDK y JRE?")
                    .explicacion("El JDK incluye el compilador y herramientas de desarrollo, mientras que el JRE solo permite ejecutar aplicaciones.")
                    .difficulty(Difficulty.MEDIUM)
                    .options(Arrays.asList(
                            Option.builder().text("JDK ejecuta, JRE compila").letter(LetterOption.A).build(),
                            Option.builder().text("JDK incluye compilador, JRE solo ejecuta").letter(LetterOption.B).build(),
                            Option.builder().text("JRE es más nuevo que JDK").letter(LetterOption.C).build(),
                            Option.builder().text("Son equivalentes").letter(LetterOption.D).build())
                    )
                    .correctOption(Option.builder().text("JDK incluye compilador, JRE solo ejecuta").letter(LetterOption.B).build())
                    .build(),

            Pregunta.builder()
                    .id(UUID.randomUUID().toString())
                    .userId(1L)
                    .topico("Java")
                    .emoji("☕")
                    .enunciado("¿Qué modificador de acceso permite el uso dentro del mismo paquete pero no fuera de él?")
                    .explicacion("El modificador 'default' (sin palabra clave) limita el acceso al paquete.")
                    .difficulty(Difficulty.MEDIUM)
                    .options(Arrays.asList(
                            Option.builder().text("private").letter(LetterOption.A).build(),
                            Option.builder().text("protected").letter(LetterOption.B).build(),
                            Option.builder().text("default").letter(LetterOption.C).build(),
                            Option.builder().text("public").letter(LetterOption.D).build())
                    )
                    .correctOption(Option.builder().text("default").letter(LetterOption.C).build())
                    .build(),

            Pregunta.builder()
                    .id(UUID.randomUUID().toString())
                    .userId(1L)
                    .topico("Java")
                    .emoji("☕")
                    .enunciado("¿Cuál de los siguientes tipos de datos primitivos ocupa 64 bits?")
                    .explicacion("En Java, 'long' y 'double' utilizan 64 bits.")
                    .difficulty(Difficulty.LOW)
                    .options(Arrays.asList(
                            Option.builder().text("int").letter(LetterOption.A).build(),
                            Option.builder().text("short").letter(LetterOption.B).build(),
                            Option.builder().text("long").letter(LetterOption.C).build(),
                            Option.builder().text("byte").letter(LetterOption.D).build())
                    )
                    .correctOption(Option.builder().text("long").letter(LetterOption.C).build())
                    .build(),

            Pregunta.builder()
                    .id(UUID.randomUUID().toString())
                    .userId(1L)
                    .topico("Java")
                    .emoji("☕")
                    .enunciado("¿Qué interfaz funcional se utiliza en Java para representar una operación que acepta un argumento y no devuelve resultado?")
                    .explicacion("La interfaz Consumer<T> representa una operación que toma un argumento y no devuelve nada.")
                    .difficulty(Difficulty.HIGH)
                    .options(Arrays.asList(
                            Option.builder().text("Supplier<T>").letter(LetterOption.A).build(),
                            Option.builder().text("Consumer<T>").letter(LetterOption.B).build(),
                            Option.builder().text("Function<T,R>").letter(LetterOption.C).build(),
                            Option.builder().text("Predicate<T>").letter(LetterOption.D).build())
                    )
                    .correctOption(Option.builder().text("Consumer<T>").letter(LetterOption.B).build())
                    .build(),

            Pregunta.builder()
                    .id(UUID.randomUUID().toString())
                    .userId(2L)
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
                    .userId(2L)
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
                    .userId(2L)
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
                    .userId(2L)
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
                    .userId(2L)
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
                    .build(),

            Pregunta.builder()
                    .id(UUID.randomUUID().toString())
                    .userId(2L)
                    .topico("Java")
                    .emoji("☕")
                    .enunciado("¿Qué palabra clave se usa para heredar una clase en Java?")
                    .explicacion("La palabra clave 'extends' se utiliza para heredar de otra clase.")
                    .difficulty(Difficulty.LOW)
                    .options(Arrays.asList(
                            Option.builder().text("inherits").letter(LetterOption.A).build(),
                            Option.builder().text("extends").letter(LetterOption.B).build(),
                            Option.builder().text("implements").letter(LetterOption.C).build(),
                            Option.builder().text("super").letter(LetterOption.D).build())
                    )
                    .correctOption(Option.builder().text("extends").letter(LetterOption.B).build())
                    .build(),

            Pregunta.builder()
                    .id(UUID.randomUUID().toString())
                    .userId(2L)
                    .topico("Java")
                    .emoji("☕")
                    .enunciado("¿Qué interfaz debe implementar una clase para que sus objetos sean comparables de forma natural?")
                    .explicacion("La interfaz Comparable<T> define el método compareTo.")
                    .difficulty(Difficulty.MEDIUM)
                    .options(Arrays.asList(
                            Option.builder().text("Comparator<T>").letter(LetterOption.A).build(),
                            Option.builder().text("Comparable<T>").letter(LetterOption.B).build(),
                            Option.builder().text("Equals<T>").letter(LetterOption.C).build(),
                            Option.builder().text("Hashable<T>").letter(LetterOption.D).build())
                    )
                    .correctOption(Option.builder().text("Comparable<T>").letter(LetterOption.B).build())
                    .build(),

            Pregunta.builder()
                    .id(UUID.randomUUID().toString())
                    .userId(2L)
                    .topico("Java")
                    .emoji("☕")
                    .enunciado("¿Qué característica introdujo Java 8 para facilitar la programación funcional?")
                    .explicacion("Las expresiones lambda y las interfaces funcionales fueron una de las grandes novedades de Java 8.")
                    .difficulty(Difficulty.MEDIUM)
                    .options(Arrays.asList(
                            Option.builder().text("Templates").letter(LetterOption.A).build(),
                            Option.builder().text("Delegates").letter(LetterOption.B).build(),
                            Option.builder().text("Expresiones lambda").letter(LetterOption.C).build(),
                            Option.builder().text("Macros").letter(LetterOption.D).build())
                    )
                    .correctOption(Option.builder().text("Expresiones lambda").letter(LetterOption.C).build())
                    .build(),

            Pregunta.builder()
                    .id(UUID.randomUUID().toString())
                    .userId(2L)
                    .topico("Java")
                    .emoji("☕")
                    .enunciado("¿Cuál es el valor por defecto de una variable de instancia de tipo boolean en Java?")
                    .explicacion("En Java, los boolean de instancia tienen valor por defecto false.")
                    .difficulty(Difficulty.LOW)
                    .options(Arrays.asList(
                            Option.builder().text("true").letter(LetterOption.A).build(),
                            Option.builder().text("false").letter(LetterOption.B).build(),
                            Option.builder().text("null").letter(LetterOption.C).build(),
                            Option.builder().text("0").letter(LetterOption.D).build())
                    )
                    .correctOption(Option.builder().text("false").letter(LetterOption.B).build())
                    .build(),

            Pregunta.builder()
                    .id(UUID.randomUUID().toString())
                    .userId(2L)
                    .topico("Java")
                    .emoji("☕")
                    .enunciado("¿Qué tipo de clase no puede ser instanciada directamente en Java?")
                    .explicacion("Las clases abstractas no se pueden instanciar directamente, solo extender.")
                    .difficulty(Difficulty.MEDIUM)
                    .options(Arrays.asList(
                            Option.builder().text("Final").letter(LetterOption.A).build(),
                            Option.builder().text("Static").letter(LetterOption.B).build(),
                            Option.builder().text("Abstracta").letter(LetterOption.C).build(),
                            Option.builder().text("Privada").letter(LetterOption.D).build())
                    )
                    .correctOption(Option.builder().text("Abstracta").letter(LetterOption.C).build())
                    .build()
    );
}
