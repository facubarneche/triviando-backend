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
                    .opciones(Arrays.asList(
                            Opcion.builder()
                                    .opcion("1492")
                                    .correcta(true)
                                    .explicacion("Cristóbal Colón llegó a América en su primer viaje en este año.")
                                    .build(),
                            Opcion.builder()
                                    .opcion("1521")
                                    .correcta(false)
                                    .explicacion("Este año fue la caída de Tenochtitlán.")
                                    .build(),
                            Opcion.builder()
                                    .opcion("1607")
                                    .correcta(false)
                                    .explicacion("Este año se fundó Jamestown.")
                                    .build(),
                            Opcion.builder()
                                    .opcion("1776")
                                    .correcta(false)
                                    .explicacion("Este año fue la independencia de EEUU.")
                                    .build())
                    )
                    .build(),

            Pregunta.builder()
                    .id(UUID.randomUUID().toString())
                    .topico("Historia")
                    .enunciado("¿Quién fue el primer presidente de los Estados Unidos?")
                    .opciones(Arrays.asList(
                            Opcion.builder()
                                    .opcion("George Washington")
                                    .correcta(true)
                                    .explicacion("George Washington fue el primer presidente de los Estados Unidos, sirviendo desde 1789 hasta 1797.")
                                    .build(),
                            Opcion.builder()
                                    .opcion("Thomas Jefferson")
                                    .correcta(false)
                                    .explicacion("Thomas Jefferson fue el tercer presidente de los Estados Unidos.")
                                    .build(),
                            Opcion.builder()
                                    .opcion("Abraham Lincoln")
                                    .correcta(false)
                                    .explicacion("Abraham Lincoln fue el decimosexto presidente de los Estados Unidos.")
                                    .build(),
                            Opcion.builder()
                                    .opcion("John Adams")
                                    .correcta(false)
                                    .explicacion("John Adams fue el segundo presidente de los Estados Unidos.")
                                    .build())
                    )
                    .build(),

            Pregunta.builder()
                    .id(UUID.randomUUID().toString())
                    .topico("Historia")
                    .enunciado("¿En qué año comenzó la Segunda Guerra Mundial?")
                    .opciones(Arrays.asList(
                            Opcion.builder()
                                    .opcion("1939")
                                    .correcta(true)
                                    .explicacion("La Segunda Guerra Mundial comenzó en septiembre de 1939 con la invasión de Polonia por Alemania.")
                                    .build(),
                            Opcion.builder()
                                    .opcion("1941")
                                    .correcta(false)
                                    .explicacion("En 1941 ocurrió el ataque a Pearl Harbor, que involucró directamente a Estados Unidos en la guerra.")
                                    .build(),
                            Opcion.builder()
                                    .opcion("1945")
                                    .correcta(false)
                                    .explicacion("1945 fue el año en que terminó la Segunda Guerra Mundial.")
                                    .build(),
                            Opcion.builder()
                                    .opcion("1938")
                                    .correcta(false)
                                    .explicacion("En 1938 ocurrió la anexión de Austria por Alemania (Anschluss).")
                                    .build())
                    )
                    .build(),

            Pregunta.builder()
                    .id(UUID.randomUUID().toString())
                    .topico("Astronomía")
                    .enunciado("¿Cuál es el planeta más grande de nuestro sistema solar?")
                    .opciones(Arrays.asList(
                            Opcion.builder()
                                    .opcion("Júpiter")
                                    .correcta(true)
                                    .explicacion("Júpiter es el planeta con mayor masa y tamaño de nuestro sistema solar.")
                                    .build(),
                            Opcion.builder()
                                    .opcion("Saturno")
                                    .correcta(false)
                                    .explicacion("Saturno es conocido por sus anillos, pero es más pequeño que Júpiter.")
                                    .build(),
                            Opcion.builder()
                                    .opcion("Marte")
                                    .correcta(false)
                                    .explicacion("Marte es un planeta terrestre significativamente más pequeño que Júpiter.")
                                    .build(),
                            Opcion.builder()
                                    .opcion("La Tierra")
                                    .correcta(false)
                                    .explicacion("La Tierra es nuestro hogar, pero es mucho más pequeña que los gigantes gaseosos.")
                                    .build())
                    )
                    .build(),

            Pregunta.builder()
                    .id(UUID.randomUUID().toString())
                    .topico("Astronomía")
                    .enunciado("¿Cuál es la galaxia más cercana a la Vía Láctea?")
                    .opciones(Arrays.asList(
                            Opcion.builder()
                                    .opcion("Galaxia de Andrómeda")
                                    .correcta(true)
                                    .explicacion("La Galaxia de Andrómeda (M31) es la galaxia espiral más grande y la más cercana a nuestra Vía Láctea.")
                                    .build(),
                            Opcion.builder()
                                    .opcion("Nube de Magallanes Mayor")
                                    .correcta(false)
                                    .explicacion("La Nube de Magallanes Mayor es una galaxia irregular, satélite de la Vía Láctea, pero no la más cercana en general.")
                                    .build(),
                            Opcion.builder()
                                    .opcion("Galaxia del Triángulo")
                                    .correcta(false)
                                    .explicacion("La Galaxia del Triángulo (M33) es otra galaxia espiral cercana, pero más distante que Andrómeda.")
                                    .build(),
                            Opcion.builder()
                                    .opcion("Galaxia del Sombrero")
                                    .correcta(false)
                                    .explicacion("La Galaxia del Sombrero (M104) está mucho más lejos que Andrómeda.")
                                    .build())
                    )
                    .build()
    );
}
