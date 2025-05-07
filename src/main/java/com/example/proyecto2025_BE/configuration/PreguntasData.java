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

@Component
@RequiredArgsConstructor
public class PreguntasData {

    public static final List<Pregunta> PREGUNTAS = Arrays.asList(
            Pregunta.builder()
                    .id(UUID.randomUUID().toString())
                    .topico("Historia")
                    .enunciado("¿Cuál fue el año en que Cristóbal Colón llegó a América?")
                    .explicacion("Cristóbal Colón llegó a América en su primer viaje en este año.")
                    .difficulty(Difficulty.HIGH)
                    .options(Arrays.asList(
                            Option.builder()
                                    .text("1521")
                                    .letter(LetterOption.A)
                                    .build(),
                            Option.builder()
                                    .text("1607")
                                    .letter(LetterOption.B)
                                    .build(),
                            Option.builder()
                                    .text("1776")
                                    .letter(LetterOption.C)
                                    .build(),
                            Option.builder()
                                    .text("1492")
                                    .letter(LetterOption.D)
                                    .build())
                    )
                    .correctOption(Option.builder()
                            .text("1492")
                            .letter(LetterOption.D)
                            .build())
                    .build(),

            Pregunta.builder()
                    .id(UUID.randomUUID().toString())
                    .topico("Historia")
                    .enunciado("¿Quién fue el primer presidente de los Estados Unidos?")
                    .explicacion("George Washington fue el primer presidente de los Estados Unidos, sirviendo desde 1789 hasta 1797.")
                    .difficulty(Difficulty.LOW)
                    .options(Arrays.asList(
                            Option.builder()
                                    .text("Thomas Jefferson")
                                    .letter(LetterOption.A)
                                    .build(),
                            Option.builder()
                                    .text("Abraham Lincoln")
                                    .letter(LetterOption.B)
                                    .build(),
                            Option.builder()
                                    .text("John Adams")
                                    .letter(LetterOption.C)
                                    .build(),
                            Option.builder()
                                    .text("George Washington")
                                    .letter(LetterOption.D)
                                    .build())
                    )
                    .correctOption(Option.builder()
                            .text("George Washington")
                            .letter(LetterOption.D)
                            .build())
                    .build(),

            Pregunta.builder()
                    .id(UUID.randomUUID().toString())
                    .topico("Historia")
                    .enunciado("¿En qué año comenzó la Segunda Guerra Mundial?")
                    .explicacion("La Segunda Guerra Mundial comenzó en septiembre de 1939 con la invasión de Polonia por Alemania.")
                    .difficulty(Difficulty.MEDIUM)
                    .options(Arrays.asList(
                            Option.builder()
                                    .text("1941")
                                    .letter(LetterOption.A)
                                    .build(),
                            Option.builder()
                                    .text("1945")
                                    .letter(LetterOption.B)
                                    .build(),
                            Option.builder()
                                    .text("1938")
                                    .letter(LetterOption.C)
                                    .build(),
                            Option.builder()
                                    .text("1939")
                                    .letter(LetterOption.D)
                                    .build())
                    )
                    .correctOption(Option.builder()
                            .text("1939")
                            .letter(LetterOption.D)
                            .build())
                    .build(),

            Pregunta.builder()
                    .id(UUID.randomUUID().toString())
                    .topico("Astronomía")
                    .enunciado("¿Cuál es el planeta más grande de nuestro sistema solar?")
                    .explicacion("Júpiter es el planeta con mayor masa y tamaño de nuestro sistema solar.")
                    .difficulty(Difficulty.MEDIUM)
                    .options(Arrays.asList(
                            Option.builder()
                                    .text("Saturno")
                                    .letter(LetterOption.A)
                                    .build(),
                            Option.builder()
                                    .text("Marte")
                                    .letter(LetterOption.B)
                                    .build(),
                            Option.builder()
                                    .text("La Tierra")
                                    .letter(LetterOption.C)
                                    .build(),
                            Option.builder()
                                    .text("Júpiter")
                                    .letter(LetterOption.D)
                                    .build())
                    )
                    .correctOption(Option.builder()
                            .text("Júpiter")
                            .letter(LetterOption.D)
                            .build())
                    .build(),

            Pregunta.builder()
                    .id(UUID.randomUUID().toString())
                    .topico("Astronomía")
                    .enunciado("¿Cuál es la galaxia más cercana a la Vía Láctea?")
                    .explicacion("La Galaxia de Andrómeda (M31) es la galaxia espiral más grande y la más cercana a nuestra Vía Láctea.")
                    .difficulty(Difficulty.LOW)
                    .options(Arrays.asList(
                            Option.builder()
                                    .text("Nube de Magallanes Mayor")
                                    .letter(LetterOption.A)
                                    .build(),
                            Option.builder()
                                    .text("Galaxia del Triángulo")
                                    .letter(LetterOption.B)
                                    .build(),
                            Option.builder()
                                    .text("Galaxia del Sombrero")
                                    .letter(LetterOption.C)
                                    .build(),
                            Option.builder()
                                    .text("Galaxia de Andrómeda")
                                    .letter(LetterOption.D)
                                    .build())
                    )
                    .correctOption(Option.builder()
                            .text("Galaxia de Andrómeda")
                            .letter(LetterOption.D)
                            .build())
                    .build()
    );
}
