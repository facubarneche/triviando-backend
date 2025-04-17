package com.example.proyecto2025_BE;

import com.example.proyecto2025_BE.model.Opcion;
import com.example.proyecto2025_BE.model.Pregunta;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Data
@Component
public class PreguntasData {


    List<Pregunta> preguntas = Arrays.asList(

            Pregunta.builder()
                    .id(UUID.randomUUID().toString())
                    .topico("Historia")
                    .enunciado("¿Cuál fue el año en que Cristóbal Colón llegó a América?")
                    .explicacion("Cristóbal Colón llegó a América en su primer viaje en este año.")
                    .opciones(Arrays.asList(
                            Opcion.builder()
                                    .opcion("1492")
                                    .correcta(true)
                                    .build(),
                            Opcion.builder()
                                    .opcion("1521")
                                    .correcta(false)
                                    .build(),
                            Opcion.builder()
                                    .opcion("1607")
                                    .correcta(false)
                                    .build(),
                            Opcion.builder()
                                    .opcion("1776")
                                    .correcta(false)
                                    .build())
                    )
                    .build(),

            Pregunta.builder()
                    .id(UUID.randomUUID().toString())
                    .topico("Historia")
                    .enunciado("¿Quién fue el primer presidente de los Estados Unidos?")
                    .explicacion("George Washington fue el primer presidente de los Estados Unidos, sirviendo desde 1789 hasta 1797.")
                    .opciones(Arrays.asList(
                            Opcion.builder()
                                    .opcion("George Washington")
                                    .correcta(true)
                                    .build(),
                            Opcion.builder()
                                    .opcion("Thomas Jefferson")
                                    .correcta(false)
                                    .build(),
                            Opcion.builder()
                                    .opcion("Abraham Lincoln")
                                    .correcta(false)
                                    .build(),
                            Opcion.builder()
                                    .opcion("John Adams")
                                    .correcta(false)
                                    .build())
                    )
                    .build(),

            Pregunta.builder()
                    .id(UUID.randomUUID().toString())
                    .topico("Historia")
                    .enunciado("¿En qué año comenzó la Segunda Guerra Mundial?")
                    .explicacion("La Segunda Guerra Mundial comenzó en septiembre de 1939 con la invasión de Polonia por Alemania.")
                    .opciones(Arrays.asList(
                            Opcion.builder()
                                    .opcion("1939")
                                    .correcta(true)
                                    .build(),
                            Opcion.builder()
                                    .opcion("1941")
                                    .correcta(false)
                                    .build(),
                            Opcion.builder()
                                    .opcion("1945")
                                    .correcta(false)
                                    .build(),
                            Opcion.builder()
                                    .opcion("1938")
                                    .correcta(false)
                                    .build())
                    )
                    .build(),

            Pregunta.builder()
                    .id(UUID.randomUUID().toString())
                    .topico("Astronomía")
                    .enunciado("¿Cuál es el planeta más grande de nuestro sistema solar?")
                    .explicacion("Júpiter es el planeta con mayor masa y tamaño de nuestro sistema solar.")
                    .opciones(Arrays.asList(
                            Opcion.builder()
                                    .opcion("Júpiter")
                                    .correcta(true)
                                    .build(),
                            Opcion.builder()
                                    .opcion("Saturno")
                                    .correcta(false)
                                    .build(),
                            Opcion.builder()
                                    .opcion("Marte")
                                    .correcta(false)
                                    .build(),
                            Opcion.builder()
                                    .opcion("La Tierra")
                                    .correcta(false)
                                    .build())
                    )
                    .build(),

            Pregunta.builder()
                    .id(UUID.randomUUID().toString())
                    .topico("Astronomía")
                    .enunciado("¿Cuál es la galaxia más cercana a la Vía Láctea?")
                    .explicacion("La Galaxia de Andrómeda (M31) es la galaxia espiral más grande y la más cercana a nuestra Vía Láctea.")
                    .opciones(Arrays.asList(
                            Opcion.builder()
                                    .opcion("Galaxia de Andrómeda")
                                    .correcta(true)
                                    .build(),
                            Opcion.builder()
                                    .opcion("Nube de Magallanes Mayor")
                                    .correcta(false)
                                    .build(),
                            Opcion.builder()
                                    .opcion("Galaxia del Triángulo")
                                    .correcta(false)
                                    .build(),
                            Opcion.builder()
                                    .opcion("Galaxia del Sombrero")
                                    .correcta(false)
                                    .build())
                    )
                    .build()
    );
}
