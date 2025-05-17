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
                    .topico("Arquitectura de Microservicios")
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
                    .enunciado("¿Cuál es el propósito del patrón 'Circuit Breaker' en microservicios?")
                    .explicacion("Evitar fallos en cascada al detener las solicitudes a servicios no disponibles.")
                    .difficulty(Difficulty.MEDIUM)
                    .options(Arrays.asList(
                            Option.builder().text("Balancear la carga").letter(LetterOption.A).build(),
                            Option.builder().text("Autenticar usuarios").letter(LetterOption.B).build(),
                            Option.builder().text("Evitar fallos en cascada").letter(LetterOption.C).build(),
                            Option.builder().text("Registrar eventos").letter(LetterOption.D).build())
                    )
                    .correctOption(Option.builder().text("Evitar fallos en cascada").letter(LetterOption.C).build())
                    .build(),

            Pregunta.builder()
                    .id(UUID.randomUUID().toString())
                    .topico("Arquitectura de Microservicios")
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
                    .enunciado("¿Qué rol juega un 'API Gateway' en una arquitectura de microservicios?")
                    .explicacion("Actúa como un punto de entrada único para todos los clientes, simplificando el acceso a los servicios.")
                    .difficulty(Difficulty.MEDIUM)
                    .options(Arrays.asList(
                            Option.builder().text("Almacenar datos persistentes").letter(LetterOption.A).build(),
                            Option.builder().text("Gestionar la configuración de los servicios").letter(LetterOption.B).build(),
                            Option.builder().text("Actuar como un punto de entrada único").letter(LetterOption.C).build(),
                            Option.builder().text("Implementar la lógica de negocio").letter(LetterOption.D).build())
                    )
                    .correctOption(Option.builder().text("Actuar como un punto de entrada único").letter(LetterOption.C).build())
                    .build(),


            Pregunta.builder()
                    .id(UUID.randomUUID().toString())
                    .topico("Bases de Datos NoSQL")
                    .enunciado("¿Cuál de las siguientes NO es una categoría principal de bases de datos NoSQL?")
                    .explicacion("Las bases de datos relacionales (SQL) no pertenecen a las categorías NoSQL.")
                    .difficulty(Difficulty.LOW)
                    .options(Arrays.asList(
                            Option.builder().text("Clave-Valor").letter(LetterOption.A).build(),
                            Option.builder().text("Documento").letter(LetterOption.B).build(),
                            Option.builder().text("Relacional").letter(LetterOption.C).build(),
                            Option.builder().text("Grafo").letter(LetterOption.D).build())
                    )
                    .correctOption(Option.builder().text("Relacional").letter(LetterOption.C).build())
                    .build(),

            Pregunta.builder()
                    .id(UUID.randomUUID().toString())
                    .topico("Bases de Datos NoSQL")
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
                    .enunciado("¿Qué tipo de base de datos NoSQL se optimiza para consultas de series de tiempo?")
                    .explicacion("Las bases de datos de series de tiempo están diseñadas para manejar datos secuenciales con marcas de tiempo.")
                    .difficulty(Difficulty.MEDIUM)
                    .options(Arrays.asList(
                            Option.builder().text("Clave-Valor").letter(LetterOption.A).build(),
                            Option.builder().text("Documento").letter(LetterOption.B).build(),
                            Option.builder().text("Columna-Familiar").letter(LetterOption.C).build(),
                            Option.builder().text("Series de Tiempo").letter(LetterOption.D).build())
                    )
                    .correctOption(Option.builder().text("Series de Tiempo").letter(LetterOption.D).build())
                    .build(),

            Pregunta.builder()
                    .id(UUID.randomUUID().toString())
                    .topico("Contenerización con Docker")
                    .enunciado("¿Qué es un 'Docker container'?")
                    .explicacion("Una instancia ejecutable de una imagen de Docker.")
                    .difficulty(Difficulty.LOW)
                    .options(Arrays.asList(
                            Option.builder().text("Un archivo de configuración de red").letter(LetterOption.A).build(),
                            Option.builder().text("Un sistema operativo virtualizado completo").letter(LetterOption.B).build(),
                            Option.builder().text("Una instancia ejecutable de una imagen de Docker").letter(LetterOption.C).build(),
                            Option.builder().text("Un repositorio de imágenes de Docker").letter(LetterOption.D).build())
                    )
                    .correctOption(Option.builder().text("Una instancia ejecutable de una imagen de Docker").letter(LetterOption.C).build())
                    .build(),

            Pregunta.builder()
                    .id(UUID.randomUUID().toString())
                    .topico("Contenerización con Docker")
                    .enunciado("¿Qué archivo se utiliza para definir la configuración de una imagen de Docker?")
                    .explicacion("El Dockerfile contiene las instrucciones para construir una imagen.")
                    .difficulty(Difficulty.LOW)
                    .options(Arrays.asList(
                            Option.builder().text("docker-compose.yml").letter(LetterOption.A).build(),
                            Option.builder().text("Dockerfile").letter(LetterOption.B).build(),
                            Option.builder().text("container.config").letter(LetterOption.C).build(),
                            Option.builder().text("image.def").letter(LetterOption.D).build())
                    )
                    .correctOption(Option.builder().text("Dockerfile").letter(LetterOption.B).build())
                    .build(),

            Pregunta.builder()
                    .id(UUID.randomUUID().toString())
                    .topico("Contenerización con Docker")
                    .enunciado("¿Cuál es el propósito de 'Docker Compose'?")
                    .explicacion("Permite definir y gestionar aplicaciones multi-contenedor.")
                    .difficulty(Difficulty.MEDIUM)
                    .options(Arrays.asList(
                            Option.builder().text("Construir imágenes de Docker").letter(LetterOption.A).build(),
                            Option.builder().text("Gestionar redes de Docker").letter(LetterOption.B).build(),
                            Option.builder().text("Definir y gestionar aplicaciones multi-contenedor").letter(LetterOption.C).build(),
                            Option.builder().text("Almacenar imágenes de Docker").letter(LetterOption.D).build())
                    )
                    .correctOption(Option.builder().text("Definir y gestionar aplicaciones multi-contenedor").letter(LetterOption.C).build())
                    .build(),

            Pregunta.builder()
                    .id(UUID.randomUUID().toString())
                    .topico("Contenerización con Docker")
                    .enunciado("¿Qué comando de Docker se utiliza para construir una imagen a partir de un Dockerfile?")
                    .explicacion("'docker build' se utiliza para construir imágenes.")
                    .difficulty(Difficulty.LOW)
                    .options(Arrays.asList(
                            Option.builder().text("docker run").letter(LetterOption.A).build(),
                            Option.builder().text("docker pull").letter(LetterOption.B).build(),
                            Option.builder().text("docker build").letter(LetterOption.C).build(),
                            Option.builder().text("docker push").letter(LetterOption.D).build())
                    )
                    .correctOption(Option.builder().text("docker build").letter(LetterOption.C).build())
                    .build(),

            Pregunta.builder()
                    .id(UUID.randomUUID().toString())
                    .topico("Contenerización con Docker")
                    .enunciado("¿Qué comando de Docker se utiliza para ejecutar una imagen y crear un contenedor?")
                    .explicacion("'docker run' inicia un contenedor a partir de una imagen.")
                    .difficulty(Difficulty.LOW)
                    .options(Arrays.asList(
                            Option.builder().text("docker build").letter(LetterOption.A).build(),
                            Option.builder().text("docker start").letter(LetterOption.B).build(),
                            Option.builder().text("docker run").letter(LetterOption.C).build(),
                            Option.builder().text("docker exec").letter(LetterOption.D).build())
                    )
                    .correctOption(Option.builder().text("docker run").letter(LetterOption.C).build())
                    .build(),

            Pregunta.builder()
                    .id(UUID.randomUUID().toString())
                    .topico("Contenerización con Docker")
                    .enunciado("¿Cuál es la diferencia principal entre una imagen de Docker y un contenedor de Docker?")
                    .explicacion("Una imagen es una plantilla estática, un contenedor es una instancia en ejecución.")
                    .difficulty(Difficulty.MEDIUM)
                    .options(Arrays.asList(
                            Option.builder().text("No hay diferencia, son términos intercambiables").letter(LetterOption.A).build(),
                            Option.builder().text("Una imagen es la instancia en ejecución, el contenedor es la plantilla").letter(LetterOption.B).build(),
                            Option.builder().text("Una imagen es una plantilla estática, un contenedor es una instancia en ejecución").letter(LetterOption.C).build(),
                            Option.builder().text("Un contenedor solo puede tener una imagen base").letter(LetterOption.D).build())
                    )
                    .correctOption(Option.builder().text("Una imagen es una plantilla estática, un contenedor es una instancia en ejecución").letter(LetterOption.C).build())
                    .build(),

            Pregunta.builder()
                    .id(UUID.randomUUID().toString())
                    .topico("Contenerización con Docker")
                    .enunciado("¿Qué comando se utiliza para detener un contenedor en ejecución?")
                    .explicacion("'docker stop' envía una señal de terminación al contenedor.")
                    .difficulty(Difficulty.LOW)
                    .options(Arrays.asList(
                            Option.builder().text("docker kill").letter(LetterOption.A).build(),
                            Option.builder().text("docker pause").letter(LetterOption.B).build(),
                            Option.builder().text("docker stop").letter(LetterOption.C).build(),
                            Option.builder().text("docker rm").letter(LetterOption.D).build())
                    )
                    .correctOption(Option.builder().text("docker stop").letter(LetterOption.C).build())
                    .build(),

            Pregunta.builder()
                    .id(UUID.randomUUID().toString())
                    .topico("Contenerización con Docker")
                    .enunciado("¿Cuál es el propósito de los 'volúmenes' en Docker?")
                    .explicacion("Los volúmenes se utilizan para persistir datos más allá del ciclo de vida de un contenedor.")
                    .difficulty(Difficulty.MEDIUM)
                    .options(Arrays.asList(
                            Option.builder().text("Gestionar la red entre contenedores").letter(LetterOption.A).build(),
                            Option.builder().text("Definir variables de entorno para los contenedores").letter(LetterOption.B).build(),
                            Option.builder().text("Persistir datos más allá del ciclo de vida del contenedor").letter(LetterOption.C).build(),
                            Option.builder().text("Limitar el uso de recursos del contenedor (CPU, memoria)").letter(LetterOption.D).build())
                    )
                    .correctOption(Option.builder().text("Persistir datos más allá del ciclo de vida del contenedor").letter(LetterOption.C).build())
                    .build(),

            Pregunta.builder()
                    .id(UUID.randomUUID().toString())
                    .topico("Contenerización con Docker")
                    .enunciado("¿Qué comando se utiliza para eliminar un contenedor detenido?")
                    .explicacion("'docker rm' elimina contenedores.")
                    .difficulty(Difficulty.LOW)
                    .options(Arrays.asList(
                            Option.builder().text("docker rmi").letter(LetterOption.A).build(),
                            Option.builder().text("docker stop").letter(LetterOption.B).build(),
                            Option.builder().text("docker rm").letter(LetterOption.C).build(),
                            Option.builder().text("docker prune").letter(LetterOption.D).build())
                    )
                    .correctOption(Option.builder().text("docker rm").letter(LetterOption.C).build())
                    .build(),

            Pregunta.builder()
                    .id(UUID.randomUUID().toString())
                    .topico("Contenerización con Docker")
                    .enunciado("¿Qué es un 'Docker Registry'?")
                    .explicacion("Un servicio para almacenar y distribuir imágenes de Docker.")
                    .difficulty(Difficulty.LOW)
                    .options(Arrays.asList(
                            Option.builder().text("Una herramienta para orquestar múltiples contenedores").letter(LetterOption.A).build(),
                            Option.builder().text("Un formato de archivo para definir imágenes de Docker").letter(LetterOption.B).build(),
                            Option.builder().text("Un servicio para almacenar y distribuir imágenes de Docker").letter(LetterOption.C).build(),
                            Option.builder().text("Una interfaz gráfica para gestionar contenedores").letter(LetterOption.D).build())
                    )
                    .correctOption(Option.builder().text("Un servicio para almacenar y distribuir imágenes de Docker").letter(LetterOption.C).build())
                    .build(),

            Pregunta.builder()
                    .id(UUID.randomUUID().toString())
                    .topico("Seguridad en Aplicaciones Web")
                    .enunciado("¿Qué vulnerabilidad web permite a un atacante inyectar scripts maliciosos en sitios web vistos por otros usuarios?")
                    .explicacion("Cross-Site Scripting (XSS) explota la falta de validación de entrada y salida.")
                    .difficulty(Difficulty.MEDIUM)
                    .options(Arrays.asList(
                            Option.builder().text("Inyección SQL").letter(LetterOption.A).build(),
                            Option.builder().text("Cross-Site Request Forgery (CSRF)").letter(LetterOption.B).build(),
                            Option.builder().text("Cross-Site Scripting (XSS)").letter(LetterOption.C).build(),
                            Option.builder().text("Denegación de Servicio (DoS)").letter(LetterOption.D).build())
                    )
                    .correctOption(Option.builder().text("Cross-Site Scripting (XSS)").letter(LetterOption.C).build())
                    .build(),

            Pregunta.builder()
                    .id(UUID.randomUUID().toString())
                    .topico("Seguridad en Aplicaciones Web")
                    .enunciado("¿Cuál es el propósito principal de HTTPS en la comunicación web?")
                    .explicacion("Proporcionar cifrado para proteger la confidencialidad e integridad de los datos.")
                    .difficulty(Difficulty.LOW)
                    .options(Arrays.asList(
                            Option.builder().text("Mejorar el rendimiento del sitio web").letter(LetterOption.A).build(),
                            Option.builder().text("Asegurar la disponibilidad del servidor").letter(LetterOption.B).build(),
                            Option.builder().text("Proporcionar cifrado para la comunicación").letter(LetterOption.C).build(),
                            Option.builder().text("Optimizar la indexación por motores de búsqueda").letter(LetterOption.D).build())
                    )
                    .correctOption(Option.builder().text("Proporcionar cifrado para la comunicación").letter(LetterOption.C).build())
                    .build(),

            Pregunta.builder()
                    .id(UUID.randomUUID().toString())
                    .topico("Seguridad en Aplicaciones Web")
                    .enunciado("¿Qué técnica se utiliza para prevenir ataques de fuerza bruta en formularios de inicio de sesión?")
                    .explicacion("El rate limiting restringe el número de intentos en un período de tiempo.")
                    .difficulty(Difficulty.MEDIUM)
                    .options(Arrays.asList(
                            Option.builder().text("Validación de entrada").letter(LetterOption.A).build(),
                            Option.builder().text("Rate limiting").letter(LetterOption.B).build(),
                            Option.builder().text("Sanitización de salida").letter(LetterOption.C).build(),
                            Option.builder().text("Uso de cookies HTTP only").letter(LetterOption.D).build())
                    )
                    .correctOption(Option.builder().text("Rate limiting").letter(LetterOption.B).build())
                    .build(),

            Pregunta.builder()
                    .id(UUID.randomUUID().toString())
                    .topico("Seguridad en Aplicaciones Web")
                    .enunciado("¿Qué significa OWASP Top Ten?")
                    .explicacion("Una lista de las diez vulnerabilidades de seguridad web más críticas.")
                    .difficulty(Difficulty.MEDIUM)
                    .options(Arrays.asList(
                            Option.builder().text("Un estándar para el desarrollo seguro de software").letter(LetterOption.A).build(),
                            Option.builder().text("Una herramienta para escanear vulnerabilidades web").letter(LetterOption.B).build(),
                            Option.builder().text("Una lista de las diez vulnerabilidades de seguridad web más críticas").letter(LetterOption.C).build(),
                            Option.builder().text("Una guía para la configuración segura de servidores web").letter(LetterOption.D).build())
                    )
                    .correctOption(Option.builder().text("Una lista de las diez vulnerabilidades de seguridad web más críticas").letter(LetterOption.C).build())
                    .build(),

            Pregunta.builder()
                    .id(UUID.randomUUID().toString())
                    .topico("Seguridad en Aplicaciones Web")
                    .enunciado("¿Qué tipo de ataque se basa en enviar una gran cantidad de solicitudes a un servidor para hacerlo inaccesible?")
                    .explicacion("Un ataque de Denegación de Servicio (DoS) busca agotar los recursos del servidor.")
                    .difficulty(Difficulty.MEDIUM)
                    .options(Arrays.asList(
                            Option.builder().text("Inyección de comandos").letter(LetterOption.A).build(),
                            Option.builder().text("Cross-Site Request Forgery (CSRF)").letter(LetterOption.B).build(),
                            Option.builder().text("Denegación de Servicio (DoS)").letter(LetterOption.C).build(),
                            Option.builder().text("Divulgación de información sensible").letter(LetterOption.D).build())
                    )
                    .correctOption(Option.builder().text("Denegación de Servicio (DoS)").letter(LetterOption.C).build())
                    .build(),

// Tópico: Computación en la Nube
            Pregunta.builder()
                    .id(UUID.randomUUID().toString())
                    .topico("Computación en la Nube")
                    .enunciado("¿Cuál de los siguientes es un modelo de servicio de computación en la nube?")
                    .explicacion("Infraestructura como Servicio (IaaS) proporciona recursos computacionales fundamentales.")
                    .difficulty(Difficulty.LOW)
                    .options(Arrays.asList(
                            Option.builder().text("Desarrollo Front-end").letter(LetterOption.A).build(),
                            Option.builder().text("Infraestructura como Servicio (IaaS)").letter(LetterOption.B).build(),
                            Option.builder().text("Gestión de Bases de Datos").letter(LetterOption.C).build(),
                            Option.builder().text("Diseño de Redes").letter(LetterOption.D).build())
                    )
                    .correctOption(Option.builder().text("Infraestructura como Servicio (IaaS)").letter(LetterOption.B).build())
                    .build(),

            Pregunta.builder()
                    .id(UUID.randomUUID().toString())
                    .topico("Computación en la Nube")
                    .enunciado("¿Qué característica clave de la computación en la nube permite escalar recursos rápidamente según la demanda?")
                    .explicacion("La elasticidad permite aumentar o disminuir los recursos de forma dinámica.")
                    .difficulty(Difficulty.MEDIUM)
                    .options(Arrays.asList(
                            Option.builder().text("Virtualización").letter(LetterOption.A).build(),
                            Option.builder().text("Multitenencia").letter(LetterOption.B).build(),
                            Option.builder().text("Elasticidad").letter(LetterOption.C).build(),
                            Option.builder().text("Orquestación").letter(LetterOption.D).build())
                    )
                    .correctOption(Option.builder().text("Elasticidad").letter(LetterOption.C).build())
                    .build(),

            Pregunta.builder()
                    .id(UUID.randomUUID().toString())
                    .topico("Computación en la Nube")
                    .enunciado("¿Cuál es un ejemplo de un proveedor de servicios de Plataforma como Servicio (PaaS)?")
                    .explicacion("Heroku proporciona una plataforma para desplegar y gestionar aplicaciones.")
                    .difficulty(Difficulty.MEDIUM)
                    .options(Arrays.asList(
                            Option.builder().text("Amazon EC2").letter(LetterOption.A).build(),
                            Option.builder().text("Amazon S3").letter(LetterOption.B).build(),
                            Option.builder().text("Heroku").letter(LetterOption.C).build(),
                            Option.builder().text("Microsoft Azure Virtual Machines").letter(LetterOption.D).build())
                    )
                    .correctOption(Option.builder().text("Heroku").letter(LetterOption.C).build())
                    .build(),

            Pregunta.builder()
                    .id(UUID.randomUUID().toString())
                    .topico("Computación en la Nube")
                    .enunciado("¿Qué modelo de implementación de nube implica el uso exclusivo de recursos por una única organización?")
                    .explicacion("La nube privada ofrece mayor control y seguridad para una organización.")
                    .difficulty(Difficulty.MEDIUM)
                    .options(Arrays.asList(
                            Option.builder().text("Nube pública").letter(LetterOption.A).build(),
                            Option.builder().text("Nube híbrida").letter(LetterOption.B).build(),
                            Option.builder().text("Nube comunitaria").letter(LetterOption.C).build(),
                            Option.builder().text("Nube privada").letter(LetterOption.D).build())
                    )
                    .correctOption(Option.builder().text("Nube privada").letter(LetterOption.D).build())
                    .build(),

            Pregunta.builder()
                    .id(UUID.randomUUID().toString())
                    .topico("Computación en la Nube")
                    .enunciado("¿Qué servicio de AWS se utiliza para almacenamiento de objetos escalable?")
                    .explicacion("Amazon S3 (Simple Storage Service) es un servicio de almacenamiento de objetos.")
                    .difficulty(Difficulty.LOW)
                    .options(Arrays.asList(
                            Option.builder().text("Amazon EC2").letter(LetterOption.A).build(),
                            Option.builder().text("Amazon RDS").letter(LetterOption.B).build(),
                            Option.builder().text("Amazon S3").letter(LetterOption.C).build(),
                            Option.builder().text("AWS Lambda").letter(LetterOption.D).build())
                    )
                    .correctOption(Option.builder().text("Amazon S3").letter(LetterOption.C).build())
                    .build(),

// Tópico: Computación en la Nube (Continuación)
            Pregunta.builder()
                    .id(UUID.randomUUID().toString())
                    .topico("Computación en la Nube")
                    .enunciado("¿Qué es 'serverless computing'?")
                    .explicacion("Un modelo donde el proveedor gestiona la infraestructura y el usuario solo se preocupa por el código.")
                    .difficulty(Difficulty.MEDIUM)
                    .options(Arrays.asList(
                            Option.builder().text("Ejecutar servidores dedicados en la nube").letter(LetterOption.A).build(),
                            Option.builder().text("Gestionar completamente la infraestructura de servidores").letter(LetterOption.B).build(),
                            Option.builder().text("Ejecutar código sin gestionar explícitamente servidores").letter(LetterOption.C).build(),
                            Option.builder().text("Almacenar grandes cantidades de datos no estructurados").letter(LetterOption.D).build())
                    )
                    .correctOption(Option.builder().text("Ejecutar código sin gestionar explícitamente servidores").letter(LetterOption.C).build())
                    .build(),

            Pregunta.builder()
                    .id(UUID.randomUUID().toString())
                    .topico("Computación en la Nube")
                    .enunciado("¿Cuál es un beneficio de la multitenencia en la nube?")
                    .explicacion("Permite compartir recursos entre múltiples usuarios, reduciendo costos.")
                    .difficulty(Difficulty.LOW)
                    .options(Arrays.asList(
                            Option.builder().text("Mayor aislamiento de recursos").letter(LetterOption.A).build(),
                            Option.builder().text("Mayor control sobre la infraestructura").letter(LetterOption.B).build(),
                            Option.builder().text("Reducción de costos a través del uso compartido").letter(LetterOption.C).build(),
                            Option.builder().text("Mejor rendimiento para aplicaciones individuales").letter(LetterOption.D).build())
                    )
                    .correctOption(Option.builder().text("Reducción de costos a través del uso compartido").letter(LetterOption.C).build())
                    .build(),

            Pregunta.builder()
                    .id(UUID.randomUUID().toString())
                    .topico("Computación en la Nube")
                    .enunciado("¿Qué rol juega la virtualización en la computación en la nube?")
                    .explicacion("Permite crear múltiples instancias virtuales de recursos de hardware.")
                    .difficulty(Difficulty.LOW)
                    .options(Arrays.asList(
                            Option.builder().text("Asegurar la comunicación entre servicios").letter(LetterOption.A).build(),
                            Option.builder().text("Optimizar el almacenamiento de datos").letter(LetterOption.B).build(),
                            Option.builder().text("Crear múltiples instancias virtuales de recursos").letter(LetterOption.C).build(),
                            Option.builder().text("Gestionar la identidad y el acceso de los usuarios").letter(LetterOption.D).build())
                    )
                    .correctOption(Option.builder().text("Crear múltiples instancias virtuales de recursos").letter(LetterOption.C).build())
                    .build(),

            Pregunta.builder()
                    .id(UUID.randomUUID().toString())
                    .topico("Computación en la Nube")
                    .enunciado("¿Qué significa el término 'DevOps' en el contexto de la nube?")
                    .explicacion("Una cultura y conjunto de prácticas que automatizan e integran los procesos entre desarrollo y operaciones de TI.")
                    .difficulty(Difficulty.MEDIUM)
                    .options(Arrays.asList(
                            Option.builder().text("Un modelo de precios para servicios en la nube").letter(LetterOption.A).build(),
                            Option.builder().text("Una certificación para profesionales de la nube").letter(LetterOption.B).build(),
                            Option.builder().text("Una cultura que integra desarrollo y operaciones de TI").letter(LetterOption.C).build(),
                            Option.builder().text("Una herramienta para la gestión de la infraestructura en la nube").letter(LetterOption.D).build())
                    )
                    .correctOption(Option.builder().text("Una cultura que integra desarrollo y operaciones de TI").letter(LetterOption.C).build())
                    .build(),

            Pregunta.builder()
                    .id(UUID.randomUUID().toString())
                    .topico("Computación en la Nube")
                    .enunciado("¿Cuál es un desafío común al migrar aplicaciones a la nube?")
                    .explicacion("La complejidad de la integración con sistemas locales existentes puede ser un desafío.")
                    .difficulty(Difficulty.HIGH)
                    .options(Arrays.asList(
                            Option.builder().text("Menor necesidad de personal de TI").letter(LetterOption.A).build(),
                            Option.builder().text("Mayor facilidad para la gestión de la seguridad").letter(LetterOption.B).build(),
                            Option.builder().text("Complejidad de la integración con sistemas locales").letter(LetterOption.C).build(),
                            Option.builder().text("Reducción automática de los costos operativos").letter(LetterOption.D).build())
                    )
                    .correctOption(Option.builder().text("Complejidad de la integración con sistemas locales").letter(LetterOption.C).build())
                    .build(),
            Pregunta.builder()
                    .id(UUID.randomUUID().toString())
                    .topico("Inteligencia Artificial y Aprendizaje Automático")
                    .enunciado("¿Cuál es la principal diferencia entre aprendizaje supervisado y no supervisado?")
                    .explicacion("El aprendizaje supervisado utiliza datos etiquetados, mientras que el no supervisado no.")
                    .difficulty(Difficulty.MEDIUM)
                    .options(Arrays.asList(
                            Option.builder().text("El supervisado usa más datos").letter(LetterOption.A).build(),
                            Option.builder().text("El no supervisado es más preciso").letter(LetterOption.B).build(),
                            Option.builder().text("El supervisado usa datos etiquetados").letter(LetterOption.C).build(),
                            Option.builder().text("No hay una diferencia principal").letter(LetterOption.D).build())
                    )
                    .correctOption(Option.builder().text("El supervisado usa datos etiquetados").letter(LetterOption.C).build())
                    .build(),

            Pregunta.builder()
                    .id(UUID.randomUUID().toString())
                    .topico("Inteligencia Artificial y Aprendizaje Automático")
                    .enunciado("¿Qué algoritmo de aprendizaje automático se utiliza comúnmente para problemas de clasificación?")
                    .explicacion("La regresión lineal se usa para regresión, no clasificación.")
                    .difficulty(Difficulty.LOW)
                    .options(Arrays.asList(
                            Option.builder().text("Regresión Lineal").letter(LetterOption.A).build(),
                            Option.builder().text("Agrupamiento K-Medias").letter(LetterOption.B).build(),
                            Option.builder().text("Máquinas de Vectores de Soporte (SVM)").letter(LetterOption.C).build(),
                            Option.builder().text("Reducción de Dimensionalidad PCA").letter(LetterOption.D).build())
                    )
                    .correctOption(Option.builder().text("Máquinas de Vectores de Soporte (SVM)").letter(LetterOption.C).build())
                    .build(),

            Pregunta.builder()
                    .id(UUID.randomUUID().toString())
                    .topico("Inteligencia Artificial y Aprendizaje Automático")
                    .enunciado("¿Qué es el 'deep learning'?")
                    .explicacion("Un subcampo del aprendizaje automático que utiliza redes neuronales profundas.")
                    .difficulty(Difficulty.MEDIUM)
                    .options(Arrays.asList(
                            Option.builder().text("Aprendizaje automático con pocos datos").letter(LetterOption.A).build(),
                            Option.builder().text("Aprendizaje automático no supervisado").letter(LetterOption.B).build(),
                            Option.builder().text("Aprendizaje automático con reglas explícitas").letter(LetterOption.C).build(),
                            Option.builder().text("Aprendizaje automático con redes neuronales profundas").letter(LetterOption.D).build())
                    )
                    .correctOption(Option.builder().text("Aprendizaje automático con redes neuronales profundas").letter(LetterOption.D).build())
                    .build(),

            Pregunta.builder()
                    .id(UUID.randomUUID().toString())
                    .topico("Inteligencia Artificial y Aprendizaje Automático")
                    .enunciado("¿Qué métrica se utiliza comúnmente para evaluar el rendimiento de un modelo de clasificación binaria?")
                    .explicacion("La precisión mide la proporción de predicciones correctas.")
                    .difficulty(Difficulty.LOW)
                    .options(Arrays.asList(
                            Option.builder().text("Error cuadrático medio").letter(LetterOption.A).build(),
                            Option.builder().text("Puntuación F1").letter(LetterOption.B).build(),
                            Option.builder().text("Varianza explicada").letter(LetterOption.C).build(),
                            Option.builder().text("Entropía cruzada").letter(LetterOption.D).build())
                    )
                    .correctOption(Option.builder().text("Puntuación F1").letter(LetterOption.B).build())
                    .build(),

            Pregunta.builder()
                    .id(UUID.randomUUID().toString())
                    .topico("Inteligencia Artificial y Aprendizaje Automático")
                    .enunciado("¿Qué técnica se utiliza para evitar el 'sobreajuste' (overfitting) en modelos de aprendizaje automático?")
                    .explicacion("La regularización añade una penalización a la complejidad del modelo.")
                    .difficulty(Difficulty.MEDIUM)
                    .options(Arrays.asList(
                            Option.builder().text("Aumento de datos").letter(LetterOption.A).build(),
                            Option.builder().text("Reducción de dimensionalidad").letter(LetterOption.B).build(),
                            Option.builder().text("Regularización").letter(LetterOption.C).build(),
                            Option.builder().text("Agrupamiento").letter(LetterOption.D).build())
                    )
                    .correctOption(Option.builder().text("Regularización").letter(LetterOption.C).build())
                    .build(),

// Tópico: Inteligencia Artificial y Aprendizaje Automático (Continuación)
            Pregunta.builder()
                    .id(UUID.randomUUID().toString())
                    .topico("Inteligencia Artificial y Aprendizaje Automático")
                    .enunciado("¿Qué es una 'red neuronal convolucional' (CNN) comúnmente utilizada para?")
                    .explicacion("Las CNN son muy efectivas para el procesamiento de imágenes.")
                    .difficulty(Difficulty.MEDIUM)
                    .options(Arrays.asList(
                            Option.builder().text("Procesamiento de lenguaje natural").letter(LetterOption.A).build(),
                            Option.builder().text("Análisis de series de tiempo").letter(LetterOption.B).build(),
                            Option.builder().text("Reconocimiento de voz").letter(LetterOption.C).build(),
                            Option.builder().text("Procesamiento de imágenes").letter(LetterOption.D).build())
                    )
                    .correctOption(Option.builder().text("Procesamiento de imágenes").letter(LetterOption.D).build())
                    .build(),

            Pregunta.builder()
                    .id(UUID.randomUUID().toString())
                    .topico("Inteligencia Artificial y Aprendizaje Automático")
                    .enunciado("¿Qué es el 'aprendizaje por refuerzo'?")
                    .explicacion("Un agente aprende a tomar decisiones mediante recompensas y castigos.")
                    .difficulty(Difficulty.HIGH)
                    .options(Arrays.asList(
                            Option.builder().text("Aprendizaje basado en la similitud de datos").letter(LetterOption.A).build(),
                            Option.builder().text("Aprendizaje a través de la predicción de la siguiente secuencia").letter(LetterOption.B).build(),
                            Option.builder().text("Aprendizaje mediante recompensas y castigos").letter(LetterOption.C).build(),
                            Option.builder().text("Aprendizaje a partir de datos no etiquetados").letter(LetterOption.D).build())
                    )
                    .correctOption(Option.builder().text("Aprendizaje mediante recompensas y castigos").letter(LetterOption.C).build())
                    .build(),

            Pregunta.builder()
                    .id(UUID.randomUUID().toString())
                    .topico("Inteligencia Artificial y Aprendizaje Automático")
                    .enunciado("¿Qué técnica se utiliza para reducir la dimensionalidad de los datos manteniendo la mayor varianza posible?")
                    .explicacion("El Análisis de Componentes Principales (PCA) realiza esta tarea.")
                    .difficulty(Difficulty.MEDIUM)
                    .options(Arrays.asList(
                            Option.builder().text("Agrupamiento K-Medias").letter(LetterOption.A).build(),
                            Option.builder().text("Análisis de Componentes Principales (PCA)").letter(LetterOption.B).build(),
                            Option.builder().text("Regresión Logística").letter(LetterOption.C).build(),
                            Option.builder().text("Bosques Aleatorios").letter(LetterOption.D).build())
                    )
                    .correctOption(Option.builder().text("Análisis de Componentes Principales (PCA)").letter(LetterOption.B).build())
                    .build(),

            Pregunta.builder()
                    .id(UUID.randomUUID().toString())
                    .topico("Inteligencia Artificial y Aprendizaje Automático")
                    .enunciado("¿Qué tipo de red neuronal es adecuada para procesar secuencias de datos, como texto o series de tiempo?")
                    .explicacion("Las redes neuronales recurrentes (RNN) tienen memoria para procesar secuencias.")
                    .difficulty(Difficulty.MEDIUM)
                    .options(Arrays.asList(
                            Option.builder().text("Red Neuronal Feedforward").letter(LetterOption.A).build(),
                            Option.builder().text("Red Neuronal Convolucional (CNN)").letter(LetterOption.B).build(),
                            Option.builder().text("Red Neuronal Recurrente (RNN)").letter(LetterOption.C).build(),
                            Option.builder().text("Red de Base Radial (RBF)").letter(LetterOption.D).build())
                    )
                    .correctOption(Option.builder().text("Red Neuronal Recurrente (RNN)").letter(LetterOption.C).build())
                    .build(),

            Pregunta.builder()
                    .id(UUID.randomUUID().toString())
                    .topico("Inteligencia Artificial y Aprendizaje Automático")
                    .enunciado("¿Qué es el 'procesamiento de lenguaje natural' (NLP)?")
                    .explicacion("Un campo de la IA enfocado en la interacción entre computadoras y lenguaje humano.")
                    .difficulty(Difficulty.LOW)
                    .options(Arrays.asList(
                            Option.builder().text("La creación de interfaces gráficas de usuario").letter(LetterOption.A).build(),
                            Option.builder().text("El diseño de algoritmos de optimización").letter(LetterOption.B).build(),
                            Option.builder().text("La interacción entre computadoras y lenguaje humano").letter(LetterOption.C).build(),
                            Option.builder().text("El análisis de grandes conjuntos de datos numéricos").letter(LetterOption.D).build())
                    )
                    .correctOption(Option.builder().text("La interacción entre computadoras y lenguaje humano").letter(LetterOption.C).build())
                    .build(),

// Tópico: Inteligencia Artificial y Aprendizaje Automático (Continuación)
            Pregunta.builder()
                    .id(UUID.randomUUID().toString())
                    .topico("Inteligencia Artificial y Aprendizaje Automático")
                    .enunciado("¿Qué es un 'chatbot'?")
                    .explicacion("Un programa de computadora diseñado para simular una conversación con usuarios humanos.")
                    .difficulty(Difficulty.LOW)
                    .options(Arrays.asList(
                            Option.builder().text("Un sensor para recopilar datos ambientales").letter(LetterOption.A).build(),
                            Option.builder().text("Un sistema operativo basado en la nube").letter(LetterOption.B).build(),
                            Option.builder().text("Un programa para simular conversación humana").letter(LetterOption.C).build(),
                            Option.builder().text("Un dispositivo para controlar el hogar inteligente").letter(LetterOption.D).build())
                    )
                    .correctOption(Option.builder().text("Un programa para simular conversación humana").letter(LetterOption.C).build())
                    .build(),

            Pregunta.builder()
                    .id(UUID.randomUUID().toString())
                    .topico("Inteligencia Artificial y Aprendizaje Automático")
                    .enunciado("¿Qué técnica de NLP se utiliza para identificar y clasificar las entidades nombradas en un texto?")
                    .explicacion("El Reconocimiento de Entidades Nombradas (NER) realiza esta tarea.")
                    .difficulty(Difficulty.MEDIUM)
                    .options(Arrays.asList(
                            Option.builder().text("Análisis de sentimiento").letter(LetterOption.A).build(),
                            Option.builder().text("Modelado de temas").letter(LetterOption.B).build(),
                            Option.builder().text("Reconocimiento de Entidades Nombradas (NER)").letter(LetterOption.C).build(),
                            Option.builder().text("Traducción automática").letter(LetterOption.D).build())
                    )
                    .correctOption(Option.builder().text("Reconocimiento de Entidades Nombradas (NER)").letter(LetterOption.C).build())
                    .build(),

            Pregunta.builder()
                    .id(UUID.randomUUID().toString())
                    .topico("Inteligencia Artificial y Aprendizaje Automático")
                    .enunciado("¿Qué es el 'aprendizaje por transferencia'?")
                    .explicacion("Reutilizar el conocimiento aprendido en una tarea para mejorar el aprendizaje en otra relacionada.")
                    .difficulty(Difficulty.MEDIUM)
                    .options(Arrays.asList(
                            Option.builder().text("Entrenar un modelo en grandes conjuntos de datos").letter(LetterOption.A).build(),
                            Option.builder().text("Optimizar los hiperparámetros de un modelo").letter(LetterOption.B).build(),
                            Option.builder().text("Reutilizar el conocimiento aprendido en otra tarea").letter(LetterOption.C).build(),
                            Option.builder().text("Evaluar el rendimiento de un modelo en datos nuevos").letter(LetterOption.D).build())
                    )
                    .correctOption(Option.builder().text("Reutilizar el conocimiento aprendido en otra tarea").letter(LetterOption.C).build())
                    .build(),

            Pregunta.builder()
                    .id(UUID.randomUUID().toString())
                    .topico("Inteligencia Artificial y Aprendizaje Automático")
                    .enunciado("¿Qué desafío ético importante plantea el uso de la inteligencia artificial en la toma de decisiones?")
                    .explicacion("Los sesgos en los datos pueden llevar a decisiones injustas o discriminatorias.")
                    .difficulty(Difficulty.HIGH)
                    .options(Arrays.asList(
                            Option.builder().text("El alto costo computacional de los modelos").letter(LetterOption.A).build(),
                            Option.builder().text("La dificultad de interpretar los resultados de los modelos").letter(LetterOption.B).build(),
                            Option.builder().text("Los sesgos en los datos y la toma de decisiones injustas").letter(LetterOption.C).build(),
                            Option.builder().text("La falta de estándares en el desarrollo de modelos").letter(LetterOption.D).build())
                    )
                    .correctOption(Option.builder().text("Los sesgos en los datos y la toma de decisiones injustas").letter(LetterOption.C).build())
                    .build(),

            Pregunta.builder()
                    .id(UUID.randomUUID().toString())
                    .topico("Inteligencia Artificial y Aprendizaje Automático")
                    .enunciado("¿Qué es un 'sistema experto'?")
                    .explicacion("Un sistema de IA diseñado para simular la capacidad de resolución de problemas de un experto humano.")
                    .difficulty(Difficulty.MEDIUM)
                    .options(Arrays.asList(
                            Option.builder().text("Un algoritmo de aprendizaje no supervisado").letter(LetterOption.A).build(),
                            Option.builder().text("Un programa para la traducción automática de idiomas").letter(LetterOption.B).build(),
                            Option.builder().text("Un sistema que simula la experiencia de un experto humano").letter(LetterOption.C).build(),
                            Option.builder().text("Una técnica para la visualización de grandes conjuntos de datos").letter(LetterOption.D).build())
                    )
                    .correctOption(Option.builder().text("Un sistema que simula la experiencia de un experto humano").letter(LetterOption.C).build())
                    .build(),

            Pregunta.builder()
                    .id(UUID.randomUUID().toString())
                    .topico("Inteligencia Artificial y Aprendizaje Automático")
                    .enunciado("¿Qué es la 'ingeniería de características' (feature engineering)?")
                    .explicacion("El proceso de seleccionar, transformar y crear nuevas características relevantes para el modelo.")
                    .difficulty(Difficulty.MEDIUM)
                    .options(Arrays.asList(
                            Option.builder().text("La implementación física de redes neuronales").letter(LetterOption.A).build(),
                            Option.builder().text("La optimización del rendimiento de un modelo ya entrenado").letter(LetterOption.B).build(),
                            Option.builder().text("El proceso de seleccionar y crear características relevantes").letter(LetterOption.C).build(),
                            Option.builder().text("La recopilación y limpieza de grandes conjuntos de datos").letter(LetterOption.D).build())
                    )
                    .correctOption(Option.builder().text("El proceso de seleccionar y crear características relevantes").letter(LetterOption.C).build())
                    .build(),
            Pregunta.builder()
                    .id(UUID.randomUUID().toString())
                    .topico("Inteligencia Artificial y Aprendizaje Automático")
                    .enunciado("¿Qué es un 'modelo discriminativo' en el aprendizaje automático?")
                    .explicacion("Un modelo que aprende los límites entre diferentes clases de datos.")
                    .difficulty(Difficulty.MEDIUM)
                    .options(Arrays.asList(
                            Option.builder().text("Un modelo que genera nuevos datos similares a los de entrenamiento").letter(LetterOption.A).build(),
                            Option.builder().text("Un modelo que aprende la probabilidad conjunta de datos y etiquetas").letter(LetterOption.B).build(),
                            Option.builder().text("Un modelo que aprende los límites entre diferentes clases de datos").letter(LetterOption.C).build(),
                            Option.builder().text("Un modelo que reduce la dimensionalidad de los datos").letter(LetterOption.D).build())
                    )
                    .correctOption(Option.builder().text("Un modelo que aprende los límites entre diferentes clases de datos").letter(LetterOption.C).build())
                    .build(),

            Pregunta.builder()
                    .id(UUID.randomUUID().toString())
                    .topico("Inteligencia Artificial y Aprendizaje Automático")
                    .enunciado("¿Qué es el 'aprendizaje auto-supervisado'?")
                    .explicacion("Un tipo de aprendizaje donde las etiquetas se generan a partir de los propios datos.")
                    .difficulty(Difficulty.MEDIUM)
                    .options(Arrays.asList(
                            Option.builder().text("Aprendizaje sin ninguna etiqueta").letter(LetterOption.A).build(),
                            Option.builder().text("Aprendizaje con etiquetas proporcionadas por expertos").letter(LetterOption.B).build(),
                            Option.builder().text("Aprendizaje donde las etiquetas se generan de los datos").letter(LetterOption.C).build(),
                            Option.builder().text("Aprendizaje que utiliza solo datos numéricos").letter(LetterOption.D).build())
                    )
                    .correctOption(Option.builder().text("Aprendizaje donde las etiquetas se generan de los datos").letter(LetterOption.C).build())
                    .build(),

            Pregunta.builder()
                    .id(UUID.randomUUID().toString())
                    .topico("Inteligencia Artificial y Aprendizaje Automático")
                    .enunciado("¿Qué es la 'interpretabilidad' en el contexto de los modelos de IA?")
                    .explicacion("La capacidad de entender por qué un modelo toma ciertas decisiones.")
                    .difficulty(Difficulty.HIGH)
                    .options(Arrays.asList(
                            Option.builder().text("La precisión del modelo en datos no vistos").letter(LetterOption.A).build(),
                            Option.builder().text("La velocidad con la que el modelo realiza predicciones").letter(LetterOption.B).build(),
                            Option.builder().text("La capacidad de entender las decisiones del modelo").letter(LetterOption.C).build(),
                            Option.builder().text("La cantidad de datos utilizados para entrenar el modelo").letter(LetterOption.D).build())
                    )
                    .correctOption(Option.builder().text("La capacidad de entender las decisiones del modelo").letter(LetterOption.C).build())
                    .build(),

            Pregunta.builder()
                    .id(UUID.randomUUID().toString())
                    .topico("Inteligencia Artificial y Aprendizaje Automático")
                    .enunciado("¿Qué es un 'hiperparámetro' en el aprendizaje automático?")
                    .explicacion("Un parámetro cuyo valor se establece antes del proceso de aprendizaje.")
                    .difficulty(Difficulty.MEDIUM)
                    .options(Arrays.asList(
                            Option.builder().text("Un parámetro aprendido por el modelo durante el entrenamiento").letter(LetterOption.A).build(),
                            Option.builder().text("Un parámetro que describe las características de los datos").letter(LetterOption.B).build(),
                            Option.builder().text("Un parámetro cuyo valor se establece antes del aprendizaje").letter(LetterOption.C).build(),
                            Option.builder().text("Un parámetro que mide el rendimiento del modelo").letter(LetterOption.D).build())
                    )
                    .correctOption(Option.builder().text("Un parámetro cuyo valor se establece antes del aprendizaje").letter(LetterOption.C).build())
                    .build(),

            Pregunta.builder()
                    .id(UUID.randomUUID().toString())
                    .topico("Inteligencia Artificial y Aprendizaje Automático")
                    .enunciado("¿Qué técnica se utiliza para encontrar los mejores hiperparámetros para un modelo?")
                    .explicacion("La búsqueda de cuadrícula y la búsqueda aleatoria son métodos comunes.")
                    .difficulty(Difficulty.MEDIUM)
                    .options(Arrays.asList(
                            Option.builder().text("Validación cruzada").letter(LetterOption.A).build(),
                            Option.builder().text("Regularización L1 y L2").letter(LetterOption.B).build(),
                            Option.builder().text("Búsqueda de cuadrícula").letter(LetterOption.C).build(),
                            Option.builder().text("Aumento de datos").letter(LetterOption.D).build())
                    )
                    .correctOption(Option.builder().text("Búsqueda de cuadrícula").letter(LetterOption.C).build())
                    .build(),

            // Nuevo Tópico: Desarrollo de Software Ágil
            Pregunta.builder()
                    .id(UUID.randomUUID().toString())
                    .topico("Desarrollo de Software Ágil")
                    .enunciado("¿Cuál es el objetivo principal del desarrollo de software ágil?")
                    .explicacion("Entregar valor al cliente de forma rápida y adaptativa.")
                    .difficulty(Difficulty.LOW)
                    .options(Arrays.asList(
                            Option.builder().text("Planificación exhaustiva al inicio del proyecto").letter(LetterOption.A).build(),
                            Option.builder().text("Minimizar la documentación del software").letter(LetterOption.B).build(),
                            Option.builder().text("Entregar valor al cliente de forma rápida y adaptativa").letter(LetterOption.C).build(),
                            Option.builder().text("Seguir estrictamente un plan predefinido").letter(LetterOption.D).build())
                    )
                    .correctOption(Option.builder().text("Entregar valor al cliente de forma rápida y adaptativa").letter(LetterOption.C).build())
                    .build(),

            Pregunta.builder()
                    .id(UUID.randomUUID().toString())
                    .topico("Desarrollo de Software Ágil")
                    .enunciado("¿Qué es un 'Sprint' en Scrum?")
                    .explicacion("Un periodo de tiempo fijo durante el cual se crea un incremento de producto.")
                    .difficulty(Difficulty.LOW)
                    .options(Arrays.asList(
                            Option.builder().text("Una reunión diaria del equipo de desarrollo").letter(LetterOption.A).build(),
                            Option.builder().text("Un periodo de tiempo fijo para crear un incremento de producto").letter(LetterOption.B).build(),
                            Option.builder().text("Una revisión del producto al final de cada iteración").letter(LetterOption.C).build(),
                            Option.builder().text("Un backlog de tareas priorizadas").letter(LetterOption.D).build())
                    )
                    .correctOption(Option.builder().text("Un periodo de tiempo fijo para crear un incremento de producto").letter(LetterOption.B).build())
                    .build(),

            Pregunta.builder()
                    .id(UUID.randomUUID().toString())
                    .topico("Desarrollo de Software Ágil")
                    .enunciado("¿Cuál es el rol del 'Scrum Master'?")
                    .explicacion("Facilitar el proceso de Scrum y eliminar impedimentos.")
                    .difficulty(Difficulty.MEDIUM)
                    .options(Arrays.asList(
                            Option.builder().text("Asignar tareas al equipo de desarrollo").letter(LetterOption.A).build(),
                            Option.builder().text("Definir los requisitos del producto").letter(LetterOption.B).build(),
                            Option.builder().text("Facilitar el proceso de Scrum y eliminar impedimentos").letter(LetterOption.C).build(),
                            Option.builder().text("Evaluar el rendimiento del equipo").letter(LetterOption.D).build())
                    )
                    .correctOption(Option.builder().text("Facilitar el proceso de Scrum y eliminar impedimentos").letter(LetterOption.C).build())
                    .build(),

            Pregunta.builder()
                    .id(UUID.randomUUID().toString())
                    .topico("Desarrollo de Software Ágil")
                    .enunciado("¿Qué es el 'Product Backlog'?")
                    .explicacion("Una lista priorizada de todo el trabajo que se necesita en el producto.")
                    .difficulty(Difficulty.LOW)
                    .options(Arrays.asList(
                            Option.builder().text("El plan detallado para un Sprint").letter(LetterOption.A).build(),
                            Option.builder().text("Una lista de los miembros del equipo Scrum").letter(LetterOption.B).build(),
                            Option.builder().text("Una lista priorizada de todo el trabajo del producto").letter(LetterOption.C).build(),
                            Option.builder().text("El documento de diseño de la arquitectura del software").letter(LetterOption.D).build())
                    )
                    .correctOption(Option.builder().text("Una lista priorizada de todo el trabajo del producto").letter(LetterOption.C).build())
                    .build(),

            Pregunta.builder()
                    .id(UUID.randomUUID().toString())
                    .topico("Desarrollo de Software Ágil")
                    .enunciado("¿Qué se discute típicamente en la 'Daily Scrum' o reunión diaria?")
                    .explicacion("El equipo comparte progreso, planes para el día y obstáculos.")
                    .difficulty(Difficulty.LOW)
                    .options(Arrays.asList(
                            Option.builder().text("La planificación del próximo Sprint").letter(LetterOption.A).build(),
                            Option.builder().text("La revisión del incremento de producto").letter(LetterOption.B).build(),
                            Option.builder().text("Progreso, planes para el día y obstáculos del equipo").letter(LetterOption.C).build(),
                            Option.builder().text("La definición de los requisitos del usuario").letter(LetterOption.D).build())
                    )
                    .correctOption(Option.builder().text("Progreso, planes para el día y obstáculos del equipo").letter(LetterOption.C).build())
                    .build(),

            Pregunta.builder()
                    .id(UUID.randomUUID().toString())
                    .topico("Desarrollo de Software Ágil")
                    .enunciado("¿Qué es una 'User Story'?")
                    .explicacion("Una descripción concisa de una funcionalidad desde la perspectiva del usuario final.")
                    .difficulty(Difficulty.LOW)
                    .options(Arrays.asList(
                            Option.builder().text("Un diagrama de casos de uso detallado").letter(LetterOption.A).build(),
                            Option.builder().text("Un documento de especificación técnica").letter(LetterOption.B).build(),
                            Option.builder().text("Una descripción de funcionalidad desde la perspectiva del usuario").letter(LetterOption.C).build(),
                            Option.builder().text("Un plan de pruebas detallado").letter(LetterOption.D).build())
                    )
                    .correctOption(Option.builder().text("Una descripción de funcionalidad desde la perspectiva del usuario").letter(LetterOption.C).build())
                    .build(),

            Pregunta.builder()
                    .id(UUID.randomUUID().toString())
                    .topico("Desarrollo de Software Ágil")
                    .enunciado("¿Cuál es el propósito de la 'Sprint Review'?")
                    .explicacion("Inspeccionar el incremento de producto y adaptar el Product Backlog si es necesario.")
                    .difficulty(Difficulty.MEDIUM)
                    .options(Arrays.asList(
                            Option.builder().text("Planificar las tareas para el próximo Sprint").letter(LetterOption.A).build(),
                            Option.builder().text("Identificar y resolver los impedimentos del equipo").letter(LetterOption.B).build(),
                            Option.builder().text("Inspeccionar el incremento y adaptar el Product Backlog").letter(LetterOption.C).build(),
                            Option.builder().text("Evaluar el rendimiento individual de los miembros del equipo").letter(LetterOption.D).build())
                    )
                    .correctOption(Option.builder().text("Inspeccionar el incremento y adaptar el Product Backlog").letter(LetterOption.C).build())
                    .build(),

            Pregunta.builder()
                    .id(UUID.randomUUID().toString())
                    .topico("Desarrollo de Software Ágil")
                    .enunciado("¿Qué es la 'Sprint Retrospective'?")
                    .explicacion("Una oportunidad para el equipo de inspeccionar y adaptar sus procesos.")
                    .difficulty(Difficulty.MEDIUM)
                    .options(Arrays.asList(
                            Option.builder().text("Una reunión para definir los objetivos del Sprint").letter(LetterOption.A).build(),
                            Option.builder().text("Una sesión para probar el software desarrollado durante el Sprint").letter(LetterOption.B).build(),
                            Option.builder().text("Una oportunidad para el equipo de inspeccionar y adaptar sus procesos").letter(LetterOption.C).build(),
                            Option.builder().text("Una presentación del producto a los stakeholders").letter(LetterOption.D).build())
                    )
                    .correctOption(Option.builder().text("Una oportunidad para el equipo de inspeccionar y adaptar sus procesos").letter(LetterOption.C).build())
                    .build(),

            Pregunta.builder()
                    .id(UUID.randomUUID().toString())
                    .topico("Desarrollo de Software Ágil")
                    .enunciado("¿Qué valor promueve el Manifiesto Ágil sobre la documentación exhaustiva?")
                    .explicacion("Software funcionando sobre documentación extensiva.")
                    .difficulty(Difficulty.LOW)
                    .options(Arrays.asList(
                            Option.builder().text("Procesos y herramientas sobre individuos e interacciones").letter(LetterOption.A).build(),
                            Option.builder().text("Negociación de contratos sobre colaboración con el cliente").letter(LetterOption.B).build(),
                            Option.builder().text("Software funcionando sobre documentación extensiva").letter(LetterOption.C).build(),
                            Option.builder().text("Seguir un plan sobre responder al cambio").letter(LetterOption.D).build())
                    )
                    .correctOption(Option.builder().text("Software funcionando sobre documentación extensiva").letter(LetterOption.C).build())
                    .build(),

            Pregunta.builder()
                    .id(UUID.randomUUID().toString())
                    .topico("Desarrollo de Software Ágil")
                    .enunciado("¿Qué es 'Kanban'?")
                    .explicacion("Un método ágil que se centra en visualizar el flujo de trabajo y limitar el trabajo en curso.")
                    .difficulty(Difficulty.MEDIUM)
                    .options(Arrays.asList(
                            Option.builder().text("Un marco de trabajo para la gestión de proyectos complejos").letter(LetterOption.A).build(),
                            Option.builder().text("Un método que visualiza el flujo de trabajo y limita el trabajo en curso").letter(LetterOption.B).build(),
                            Option.builder().text("Una técnica para la estimación de tareas de desarrollo").letter(LetterOption.C).build(),
                            Option.builder().text("Un conjunto de prácticas para la integración continua").letter(LetterOption.D).build())
                    )
                    .correctOption(Option.builder().text("Un método que visualiza el flujo de trabajo y limita el trabajo en curso").letter(LetterOption.B).build())
                    .build(),

            Pregunta.builder()
                    .id(UUID.randomUUID().toString())
                    .topico("Desarrollo de Software Ágil")
                    .enunciado("¿Qué es el 'Lead Time' en Kanban?")
                    .explicacion("El tiempo total desde que se solicita una tarea hasta que se entrega.")
                    .difficulty(Difficulty.MEDIUM)
                    .options(Arrays.asList(
                            Option.builder().text("El tiempo que un elemento permanece en una columna del tablero Kanban").letter(LetterOption.A).build(),
                            Option.builder().text("El número máximo de tareas permitidas en una columna").letter(LetterOption.B).build(),
                            Option.builder().text("El tiempo total desde la solicitud hasta la entrega de una tarea").letter(LetterOption.C).build(),
                            Option.builder().text("La frecuencia con la que se revisa el tablero Kanban").letter(LetterOption.D).build())
                    )
                    .correctOption(Option.builder().text("El tiempo total desde la solicitud hasta la entrega de una tarea").letter(LetterOption.C).build())
                    .build(),

            Pregunta.builder()
                    .id(UUID.randomUUID().toString())
                    .topico("Desarrollo de Software Ágil")
                    .enunciado("¿Qué significa el principio ágil de 'Entrega continua de software funcionando'?")
                    .explicacion("Priorizar la entrega frecuente de software útil al cliente.")
                    .difficulty(Difficulty.LOW)
                    .options(Arrays.asList(
                            Option.builder().text("Documentar completamente cada característica antes de la entrega").letter(LetterOption.A).build(),
                            Option.builder().text("Entregar software al final del proyecto en una gran entrega").letter(LetterOption.B).build(),
                            Option.builder().text("Priorizar la entrega frecuente de software útil al cliente").letter(LetterOption.C).build(),
                            Option.builder().text("Obtener la aprobación del cliente para cada línea de código").letter(LetterOption.D).build())
                    )
                    .correctOption(Option.builder().text("Priorizar la entrega frecuente de software útil al cliente").letter(LetterOption.C).build())
                    .build(),

            Pregunta.builder()
                    .id(UUID.randomUUID().toString())
                    .topico("Desarrollo de Software Ágil")
                    .enunciado("¿Qué es un 'Burn-down Chart' en Scrum?")
                    .explicacion("Un gráfico que muestra el trabajo restante en un Sprint a lo largo del tiempo.")
                    .difficulty(Difficulty.MEDIUM)
                    .options(Arrays.asList(
                            Option.builder().text("Un diagrama que muestra la estructura del equipo Scrum").letter(LetterOption.A).build(),
                            Option.builder().text("Un gráfico del número de errores encontrados durante un Sprint").letter(LetterOption.B).build(),
                            Option.builder().text("Un gráfico del trabajo restante en un Sprint a lo largo del tiempo").letter(LetterOption.C).build(),
                            Option.builder().text("Un registro de las decisiones tomadas durante la Daily Scrum").letter(LetterOption.D).build())
                    )
                    .correctOption(Option.builder().text("Un gráfico del trabajo restante en un Sprint a lo largo del tiempo").letter(LetterOption.C).build())
                    .build(),

            Pregunta.builder()
                    .id(UUID.randomUUID().toString())
                    .topico("Desarrollo de Software Ágil")
                    .enunciado("¿Qué rol tiene el 'Product Owner' en Scrum?")
                    .explicacion("Maximizar el valor del producto resultante del trabajo del equipo de desarrollo.")
                    .difficulty(Difficulty.MEDIUM)
                    .options(Arrays.asList(
                            Option.builder().text("Gestionar el equipo de desarrollo y asignar tareas").letter(LetterOption.A).build(),
                            Option.builder().text("Facilitar las reuniones de Scrum y eliminar impedimentos").letter(LetterOption.B).build(),
                            Option.builder().text("Maximizar el valor del producto resultante del trabajo del equipo").letter(LetterOption.C).build(),
                            Option.builder().text("Asegurar la calidad técnica del software desarrollado").letter(LetterOption.D).build())
                    )
                    .correctOption(Option.builder().text("Maximizar el valor del producto resultante del trabajo del equipo").letter(LetterOption.C).build())
                    .build(),

            Pregunta.builder()
                    .id(UUID.randomUUID().toString())
                    .topico("Desarrollo de Software Ágil")
                    .enunciado("¿Qué se enfatiza en el Manifiesto Ágil sobre 'Procesos y herramientas'?")
                    .explicacion("Se valoran más los individuos e interacciones que los procesos y herramientas.")
                    .difficulty(Difficulty.LOW)
                    .options(Arrays.asList(
                            Option.builder().text("Los procesos y herramientas son más importantes que los individuos").letter(LetterOption.A).build(),
                            Option.builder().text("Se valora más la documentación que el software funcionando").letter(LetterOption.B).build(),
                            Option.builder().text("Se valoran más los individuos e interacciones que los procesos").letter(LetterOption.C).build(),
                            Option.builder().text("La negociación de contratos es más importante que la colaboración").letter(LetterOption.D).build())
                    )
                    .correctOption(Option.builder().text("Se valoran más los individuos e interacciones que los procesos").letter(LetterOption.C).build())
                    .build(),

            Pregunta.builder()
                    .id(UUID.randomUUID().toString())
                    .topico("Redes de Computadoras")
                    .enunciado("¿Qué modelo describe las capas de protocolos de red utilizados en Internet?")
                    .explicacion("El modelo TCP/IP es la base de la comunicación en Internet.")
                    .difficulty(Difficulty.LOW)
                    .options(Arrays.asList(
                            Option.builder().text("Modelo OSI").letter(LetterOption.A).build(),
                            Option.builder().text("Modelo Cliente-Servidor").letter(LetterOption.B).build(),
                            Option.builder().text("Modelo TCP/IP").letter(LetterOption.C).build(),
                            Option.builder().text("Modelo Peer-to-Peer").letter(LetterOption.D).build())
                    )
                    .correctOption(Option.builder().text("Modelo TCP/IP").letter(LetterOption.C).build())
                    .build(),

            Pregunta.builder()
                    .id(UUID.randomUUID().toString())
                    .topico("Redes de Computadoras")
                    .enunciado("¿Qué dispositivo de red opera en la capa de enlace de datos (Capa 2) del modelo OSI y utiliza direcciones MAC?")
                    .explicacion("Los switches utilizan direcciones MAC para reenviar tramas dentro de la misma red local.")
                    .difficulty(Difficulty.MEDIUM)
                    .options(Arrays.asList(
                            Option.builder().text("Router").letter(LetterOption.A).build(),
                            Option.builder().text("Firewall").letter(LetterOption.B).build(),
                            Option.builder().text("Switch").letter(LetterOption.C).build(),
                            Option.builder().text("Hub").letter(LetterOption.D).build())
                    )
                    .correctOption(Option.builder().text("Switch").letter(LetterOption.C).build())
                    .build(),

            Pregunta.builder()
                    .id(UUID.randomUUID().toString())
                    .topico("Redes de Computadoras")
                    .enunciado("¿Qué protocolo se utiliza para la resolución de direcciones IP a direcciones MAC dentro de una red local?")
                    .explicacion("ARP (Address Resolution Protocol) realiza esta función.")
                    .difficulty(Difficulty.MEDIUM)
                    .options(Arrays.asList(
                            Option.builder().text("DHCP").letter(LetterOption.A).build(),
                            Option.builder().text("DNS").letter(LetterOption.B).build(),
                            Option.builder().text("ARP").letter(LetterOption.C).build(),
                            Option.builder().text("ICMP").letter(LetterOption.D).build())
                    )
                    .correctOption(Option.builder().text("ARP").letter(LetterOption.C).build())
                    .build(),

            Pregunta.builder()
                    .id(UUID.randomUUID().toString())
                    .topico("Redes de Computadoras")
                    .enunciado("¿Qué significa la sigla DNS en el contexto de redes?")
                    .explicacion("Domain Name System traduce nombres de dominio a direcciones IP.")
                    .difficulty(Difficulty.LOW)
                    .options(Arrays.asList(
                            Option.builder().text("Dynamic Network Service").letter(LetterOption.A).build(),
                            Option.builder().text("Domain Network System").letter(LetterOption.B).build(),
                            Option.builder().text("Domain Name System").letter(LetterOption.C).build(),
                            Option.builder().text("Distributed Network Service").letter(LetterOption.D).build())
                    )
                    .correctOption(Option.builder().text("Domain Name System").letter(LetterOption.C).build())
                    .build(),

            Pregunta.builder()
                    .id(UUID.randomUUID().toString())
                    .topico("Redes de Computadoras")
                    .enunciado("¿Qué protocolo de la capa de transporte proporciona una comunicación confiable y orientada a la conexión?")
                    .explicacion("TCP (Transmission Control Protocol) asegura la entrega ordenada y sin errores.")
                    .difficulty(Difficulty.MEDIUM)
                    .options(Arrays.asList(
                            Option.builder().text("UDP").letter(LetterOption.A).build(),
                            Option.builder().text("IP").letter(LetterOption.B).build(),
                            Option.builder().text("TCP").letter(LetterOption.C).build(),
                            Option.builder().text("HTTP").letter(LetterOption.D).build())
                    )
                    .correctOption(Option.builder().text("TCP").letter(LetterOption.C).build())
                    .build(),

            // Nuevo Tópico: Sistemas Operativos
            Pregunta.builder()
                    .id(UUID.randomUUID().toString())
                    .topico("Sistemas Operativos")
                    .enunciado("¿Cuál es la función principal del kernel de un sistema operativo?")
                    .explicacion("El kernel es el núcleo del SO y gestiona los recursos del sistema.")
                    .difficulty(Difficulty.LOW)
                    .options(Arrays.asList(
                            Option.builder().text("Gestionar las interfaces de usuario").letter(LetterOption.A).build(),
                            Option.builder().text("Ejecutar aplicaciones de usuario").letter(LetterOption.B).build(),
                            Option.builder().text("Gestionar los recursos del sistema").letter(LetterOption.C).build(),
                            Option.builder().text("Compilar el código fuente de los programas").letter(LetterOption.D).build())
                    )
                    .correctOption(Option.builder().text("Gestionar los recursos del sistema").letter(LetterOption.C).build())
                    .build(),

            Pregunta.builder()
                    .id(UUID.randomUUID().toString())
                    .topico("Sistemas Operativos")
                    .enunciado("¿Qué es un 'proceso' en un sistema operativo?")
                    .explicacion("Una instancia en ejecución de un programa.")
                    .difficulty(Difficulty.LOW)
                    .options(Arrays.asList(
                            Option.builder().text("Un archivo ejecutable almacenado en disco").letter(LetterOption.A).build(),
                            Option.builder().text("Una unidad de almacenamiento de información").letter(LetterOption.B).build(),
                            Option.builder().text("Una instancia en ejecución de un programa").letter(LetterOption.C).build(),
                            Option.builder().text("Un componente físico de la computadora").letter(LetterOption.D).build())
                    )
                    .correctOption(Option.builder().text("Una instancia en ejecución de un programa").letter(LetterOption.C).build())
                    .build(),

            Pregunta.builder()
                    .id(UUID.randomUUID().toString())
                    .topico("Sistemas Operativos")
                    .enunciado("¿Qué técnica de gestión de memoria virtual permite que un proceso acceda a más memoria de la que está físicamente disponible?")
                    .explicacion("La paginación y la segmentación son técnicas de memoria virtual.")
                    .difficulty(Difficulty.MEDIUM)
                    .options(Arrays.asList(
                            Option.builder().text("Caché").letter(LetterOption.A).build(),
                            Option.builder().text("Paginación").letter(LetterOption.B).build(),
                            Option.builder().text("Interrupciones").letter(LetterOption.C).build(),
                            Option.builder().text("Polling").letter(LetterOption.D).build())
                    )
                    .correctOption(Option.builder().text("Paginación").letter(LetterOption.B).build())
                    .build(),

            Pregunta.builder()
                    .id(UUID.randomUUID().toString())
                    .topico("Sistemas Operativos")
                    .enunciado("¿Qué es un 'deadlock' en el contexto de sistemas operativos?")
                    .explicacion("Una situación donde dos o más procesos están bloqueados esperando recursos que otros tienen.")
                    .difficulty(Difficulty.HIGH)
                    .options(Arrays.asList(
                            Option.builder().text("Un error de programación que causa la terminación de un proceso").letter(LetterOption.A).build(),
                            Option.builder().text("Una condición donde un proceso consume todos los recursos del sistema").letter(LetterOption.B).build(),
                            Option.builder().text("Una situación donde procesos están bloqueados esperando recursos mutuamente").letter(LetterOption.C).build(),
                            Option.builder().text("Un fallo de hardware que impide el acceso a la memoria").letter(LetterOption.D).build())
                    )
                    .correctOption(Option.builder().text("Una situación donde procesos están bloqueados esperando recursos mutuamente").letter(LetterOption.C).build())
                    .build(),

            Pregunta.builder()
                    .id(UUID.randomUUID().toString())
                    .topico("Sistemas Operativos")
                    .enunciado("¿Cuál es el propósito de un sistema de archivos en un sistema operativo?")
                    .explicacion("Organizar y gestionar el almacenamiento y acceso a los datos en disco.")
                    .difficulty(Difficulty.LOW)
                    .options(Arrays.asList(
                            Option.builder().text("Gestionar la conexión de red").letter(LetterOption.A).build(),
                            Option.builder().text("Controlar los dispositivos de entrada y salida").letter(LetterOption.B).build(),
                            Option.builder().text("Organizar y gestionar el almacenamiento de datos").letter(LetterOption.C).build(),
                            Option.builder().text("Administrar la memoria principal del sistema").letter(LetterOption.D).build())
                    )
                    .correctOption(Option.builder().text("Organizar y gestionar el almacenamiento de datos").letter(LetterOption.C).build())
                    .build(),

            // Nuevo Tópico: Desarrollo Front-end
            Pregunta.builder()
                    .id(UUID.randomUUID().toString())
                    .topico("Desarrollo Front-end")
                    .enunciado("¿Cuál es el lenguaje principal utilizado para la estructura y el contenido de una página web?")
                    .explicacion("HTML (HyperText Markup Language) define la estructura.")
                    .difficulty(Difficulty.LOW)
                    .options(Arrays.asList(
                            Option.builder().text("JavaScript").letter(LetterOption.A).build(),
                            Option.builder().text("CSS").letter(LetterOption.B).build(),
                            Option.builder().text("HTML").letter(LetterOption.C).build(),
                            Option.builder().text("Python").letter(LetterOption.D).build())
                    )
                    .correctOption(Option.builder().text("HTML").letter(LetterOption.C).build())
                    .build(),

            Pregunta.builder()
                    .id(UUID.randomUUID().toString())
                    .topico("Desarrollo Front-end")
                    .enunciado("¿Qué tecnología se utiliza principalmente para el estilo y la presentación visual de una página web?")
                    .explicacion("CSS (Cascading Style Sheets) se encarga del diseño.")
                    .difficulty(Difficulty.LOW)
                    .options(Arrays.asList(
                            Option.builder().text("HTML").letter(LetterOption.A).build(),
                            Option.builder().text("JavaScript").letter(LetterOption.B).build(),
                            Option.builder().text("CSS").letter(LetterOption.C).build(),
                            Option.builder().text("SVG").letter(LetterOption.D).build())
                    )
                    .correctOption(Option.builder().text("CSS").letter(LetterOption.C).build())
                    .build(),

            Pregunta.builder()
                    .id(UUID.randomUUID().toString())
                    .topico("Desarrollo Front-end")
                    .enunciado("¿Cuál es el propósito principal de JavaScript en el desarrollo front-end?")
                    .explicacion("JavaScript añade interactividad y dinamismo a las páginas web.")
                    .difficulty(Difficulty.MEDIUM)
                    .options(Arrays.asList(
                            Option.builder().text("Definir la estructura del documento").letter(LetterOption.A).build(),
                            Option.builder().text("Aplicar estilos visuales a los elementos").letter(LetterOption.B).build(),
                            Option.builder().text("Añadir interactividad y dinamismo").letter(LetterOption.C).build(),
                            Option.builder().text("Gestionar bases de datos del lado del cliente").letter(LetterOption.D).build())
                    )
                    .correctOption(Option.builder().text("Añadir interactividad y dinamismo").letter(LetterOption.C).build())
                    .build(),

            Pregunta.builder()
                    .id(UUID.randomUUID().toString())
                    .topico("Desarrollo Front-end")
                    .enunciado("¿Qué es un 'framework' de JavaScript como React o Angular?")
                    .explicacion("Una biblioteca o conjunto de herramientas que facilita la construcción de interfaces de usuario complejas.")
                    .difficulty(Difficulty.MEDIUM)
                    .options(Arrays.asList(
                            Option.builder().text("Un lenguaje de programación completo").letter(LetterOption.A).build(),
                            Option.builder().text("Un sistema de gestión de bases de datos").letter(LetterOption.B).build(),
                            Option.builder().text("Una biblioteca o conjunto de herramientas para UI complejas").letter(LetterOption.C).build(),
                            Option.builder().text("Un preprocesador de CSS").letter(LetterOption.D).build())
                    )
                    .correctOption(Option.builder().text("Una biblioteca o conjunto de herramientas para UI complejas").letter(LetterOption.C).build())
                    .build(),

            Pregunta.builder()
                    .id(UUID.randomUUID().toString())
                    .topico("Desarrollo Front-end")
                    .enunciado("¿Qué significa el término 'responsive design' en el desarrollo web?")
                    .explicacion("Diseñar sitios web que se adapten a diferentes tamaños de pantalla y dispositivos.")
                    .difficulty(Difficulty.LOW)
                    .options(Arrays.asList(
                            Option.builder().text("Crear sitios web con animaciones complejas").letter(LetterOption.A).build(),
                            Option.builder().text("Optimizar el rendimiento de carga de las imágenes").letter(LetterOption.B).build(),
                            Option.builder().text("Diseñar sitios web adaptables a diferentes pantallas").letter(LetterOption.C).build(),
                            Option.builder().text("Utilizar una paleta de colores específica").letter(LetterOption.D).build())
                    )
                    .correctOption(Option.builder().text("Diseñar sitios web adaptables a diferentes pantallas").letter(LetterOption.C).build())
                    .build(),

            // Nuevo Tópico: Desarrollo Back-end
            Pregunta.builder()
                    .id(UUID.randomUUID().toString())
                    .topico("Desarrollo Back-end")
                    .enunciado("¿Cuál es la principal responsabilidad del desarrollo back-end?")
                    .explicacion("Gestionar la lógica del servidor, bases de datos y la API.")
                    .difficulty(Difficulty.LOW)
                    .options(Arrays.asList(
                            Option.builder().text("Crear la interfaz de usuario de un sitio web").letter(LetterOption.A).build(),
                            Option.builder().text("Gestionar la lógica del servidor, bases de datos y la API").letter(LetterOption.B).build(),
                            Option.builder().text("Diseñar la experiencia del usuario").letter(LetterOption.C).build(),
                            Option.builder().text("Optimizar el rendimiento del navegador").letter(LetterOption.D).build())
                    )
                    .correctOption(Option.builder().text("Gestionar la lógica del servidor, bases de datos y la API").letter(LetterOption.B).build())
                    .build(),

            Pregunta.builder()
                    .id(UUID.randomUUID().toString())
                    .topico("Desarrollo Back-end")
                    .enunciado("¿Qué tipo de lenguaje de programación se utiliza comúnmente en el desarrollo back-end?")
                    .explicacion("Python, Java, Node.js y Ruby son ejemplos populares.")
                    .difficulty(Difficulty.LOW)
                    .options(Arrays.asList(
                            Option.builder().text("HTML").letter(LetterOption.A).build(),
                            Option.builder().text("CSS").letter(LetterOption.B).build(),
                            Option.builder().text("Python").letter(LetterOption.C).build(),
                            Option.builder().text("Assembly").letter(LetterOption.D).build())
                    )
                    .correctOption(Option.builder().text("Python").letter(LetterOption.C).build())
                    .build(),

            Pregunta.builder()
                    .id(UUID.randomUUID().toString())
                    .topico("Desarrollo Back-end")
                    .enunciado("¿Qué es una 'API' (Interfaz de Programación de Aplicaciones) en el contexto del back-end?")
                    .explicacion("Un conjunto de reglas que permiten la comunicación entre diferentes sistemas de software.")
                    .difficulty(Difficulty.MEDIUM)
                    .options(Arrays.asList(
                            Option.builder().text("Una biblioteca de funciones para el front-end").letter(LetterOption.A).build(),
                            Option.builder().text("Un sistema operativo para servidores").letter(LetterOption.B).build(),
                            Option.builder().text("Un conjunto de reglas para la comunicación entre software").letter(LetterOption.C).build(),
                            Option.builder().text("Una herramienta para el diseño de bases de datos").letter(LetterOption.D).build())
                    )
                    .correctOption(Option.builder().text("Un conjunto de reglas para la comunicación entre software").letter(LetterOption.C).build())
                    .build(),

            Pregunta.builder()
                    .id(UUID.randomUUID().toString())
                    .topico("Desarrollo Back-end")
                    .enunciado("¿Qué es un 'framework' back-end como Django (Python) o Spring (Java)?")
                    .explicacion("Un conjunto de herramientas y convenciones que facilitan la construcción de aplicaciones del lado del servidor.")
                    .difficulty(Difficulty.MEDIUM)
                    .options(Arrays.asList(
                            Option.builder().text("Un lenguaje de programación interpretado").letter(LetterOption.A).build(),
                            Option.builder().text("Un sistema de gestión de bases de datos relacional").letter(LetterOption.B).build(),
                            Option.builder().text("Un conjunto de herramientas para construir aplicaciones del servidor").letter(LetterOption.C).build(),
                            Option.builder().text("Una biblioteca para la manipulación del DOM").letter(LetterOption.D).build())
                    )
                    .correctOption(Option.builder().text("Un conjunto de herramientas para construir aplicaciones del servidor").letter(LetterOption.C).build())
                    .build(),

            Pregunta.builder()
                    .id(UUID.randomUUID().toString())
                    .topico("Desarrollo Back-end")
                    .enunciado("¿Qué significa el término 'middleware' en el contexto de frameworks back-end?")
                    .explicacion("Componentes que se ejecutan durante el procesamiento de una solicitud HTTP, con diversas funciones.")
                    .difficulty(Difficulty.MEDIUM)
                    .options(Arrays.asList(
                            Option.builder().text("La capa de la aplicación que interactúa con la base de datos").letter(LetterOption.A).build(),
                            Option.builder().text("Componentes que se ejecutan durante el procesamiento de una solicitud HTTP").letter(LetterOption.B).build(),
                            Option.builder().text("El software que gestiona la infraestructura del servidor").letter(LetterOption.C).build(),
                            Option.builder().text("La parte del framework responsable del enrutamiento de URLs").letter(LetterOption.D).build())
                    )
                    .correctOption(Option.builder().text("Componentes que se ejecutan durante el procesamiento de una solicitud HTTP").letter(LetterOption.B).build())
                    .build(),

            Pregunta.builder()
                    .id(UUID.randomUUID().toString())
                    .topico("Desarrollo Back-end")
                    .enunciado("¿Qué patrón de diseño arquitectónico separa la interfaz de usuario, la lógica de negocio y la capa de datos?")
                    .explicacion("El patrón MVC (Modelo-Vista-Controlador) organiza el código de esta manera.")
                    .difficulty(Difficulty.MEDIUM)
                    .options(Arrays.asList(
                            Option.builder().text("Arquitectura de microservicios").letter(LetterOption.A).build(),
                            Option.builder().text("Patrón Modelo-Vista-Controlador (MVC)").letter(LetterOption.B).build(),
                            Option.builder().text("Arquitectura orientada a eventos").letter(LetterOption.C).build(),
                            Option.builder().text("Patrón fachada").letter(LetterOption.D).build())
                    )
                    .correctOption(Option.builder().text("Patrón Modelo-Vista-Controlador (MVC)").letter(LetterOption.B).build())
                    .build(),

            Pregunta.builder()
                    .id(UUID.randomUUID().toString())
                    .topico("Desarrollo Back-end")
                    .enunciado("¿Qué es la autenticación en el contexto de la seguridad back-end?")
                    .explicacion("El proceso de verificar la identidad de un usuario.")
                    .difficulty(Difficulty.LOW)
                    .options(Arrays.asList(
                            Option.builder().text("El proceso de cifrar la comunicación").letter(LetterOption.A).build(),
                            Option.builder().text("El proceso de verificar la identidad de un usuario").letter(LetterOption.B).build(),
                            Option.builder().text("El proceso de autorizar el acceso a recursos").letter(LetterOption.C).build(),
                            Option.builder().text("El proceso de registrar la actividad del usuario").letter(LetterOption.D).build())
                    )
                    .correctOption(Option.builder().text("El proceso de verificar la identidad de un usuario").letter(LetterOption.B).build())
                    .build(),

            Pregunta.builder()
                    .id(UUID.randomUUID().toString())
                    .topico("Desarrollo Back-end")
                    .enunciado("¿Qué es la autorización en el contexto de la seguridad back-end?")
                    .explicacion("El proceso de determinar qué acciones puede realizar un usuario autenticado.")
                    .difficulty(Difficulty.MEDIUM)
                    .options(Arrays.asList(
                            Option.builder().text("El proceso de verificar la identidad del servidor").letter(LetterOption.A).build(),
                            Option.builder().text("El proceso de asegurar la integridad de los datos").letter(LetterOption.B).build(),
                            Option.builder().text("El proceso de determinar los permisos de un usuario autenticado").letter(LetterOption.C).build(),
                            Option.builder().text("El proceso de proteger contra ataques de fuerza bruta").letter(LetterOption.D).build())
                    )
                    .correctOption(Option.builder().text("El proceso de determinar los permisos de un usuario autenticado").letter(LetterOption.C).build())
                    .build(),

            Pregunta.builder()
                    .id(UUID.randomUUID().toString())
                    .topico("Desarrollo Back-end")
                    .enunciado("¿Qué son los 'ORMs' (Object-Relational Mappers) en el desarrollo back-end?")
                    .explicacion("Bibliotecas que facilitan la interacción entre el código orientado a objetos y las bases de datos relacionales.")
                    .difficulty(Difficulty.MEDIUM)
                    .options(Arrays.asList(
                            Option.builder().text("Herramientas para la creación de APIs RESTful").letter(LetterOption.A).build(),
                            Option.builder().text("Sistemas de gestión de colas de mensajes").letter(LetterOption.B).build(),
                            Option.builder().text("Bibliotecas para interactuar con bases de datos relacionales").letter(LetterOption.C).build(),
                            Option.builder().text("Servidores web para desplegar aplicaciones back-end").letter(LetterOption.D).build())
                    )
                    .correctOption(Option.builder().text("Bibliotecas para interactuar con bases de datos relacionales").letter(LetterOption.C).build())
                    .build(),

            Pregunta.builder()
                    .id(UUID.randomUUID().toString())
                    .topico("Desarrollo Back-end")
                    .enunciado("¿Qué es una API 'RESTful'?")
                    .explicacion("Una interfaz que sigue los principios de la arquitectura REST para la comunicación.")
                    .difficulty(Difficulty.MEDIUM)
                    .options(Arrays.asList(
                            Option.builder().text("Un tipo de base de datos NoSQL").letter(LetterOption.A).build(),
                            Option.builder().text("Una interfaz que utiliza SOAP para la comunicación").letter(LetterOption.B).build(),
                            Option.builder().text("Una interfaz que sigue los principios de la arquitectura REST").letter(LetterOption.C).build(),
                            Option.builder().text("Un protocolo para la transferencia de archivos").letter(LetterOption.D).build())
                    )
                    .correctOption(Option.builder().text("Una interfaz que sigue los principios de la arquitectura REST").letter(LetterOption.C).build())
                    .build()
            );
}
