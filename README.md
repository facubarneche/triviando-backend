# 📚 Flashcards - Proyecto 2025

**Proyecto de Software – TPI – UNSAM**  
Grupo **Nullpointer**

---

## ✨ Descripción

**Flashcards** es una aplicación web *mobile first* diseñada para fomentar el hábito de estudio mediante la gamificación del aprendizaje. A partir de una temática ingresada por el usuario, la app genera automáticamente preguntas y desafíos que ayudan a reforzar conocimientos de manera divertida y competitiva.

---

## 🎯 Objetivos

- Facilitar la creación de hábitos de estudio continuos.
- Utilizar técnicas de gamificación (puntos, rankings, recompensas).
- Incentivar la competencia entre usuarios y el autoaprendizaje.

---

## 🧠 Funcionalidades principales

- Generación automática de preguntas usando **Nomic Embeddings** según el tema que elija el usuario.
- Modo de juego individual (con posibilidad de expansión a otras modalidades).
- Sistema de puntuación y ranking general por temática.
- Interfaz web adaptable a dispositivos móviles.
- Recordatorios y notificaciones (deseado para versiones futuras).

---

## 🧱 Arquitectura

El backend está construido con:

- **Java Spring Boot**
- **Base de datos relacional**: SQL (MySQL/PostgreSQL)
- **Base de datos NoSQL**: MongoDB
- **Integración de IA**: Nomic Embeddings para generación de preguntas

> A futuro se espera incluir nuevas modalidades de juego, más personalización para el usuario y opciones de suscripción con funciones premium.

---

## 🧪 Riesgos identificados

- Carga excesiva de solicitudes a la IA, afectando el rendimiento.
- Baja adopción por parte de los usuarios.
- Falta de motivación para el uso sostenido de la aplicación.

---

## 🚀 Oportunidades

- Colaboración con universidades y centros educativos.
- Extensión de funcionalidades hacia modelos pagos.
- Mejora general del aprendizaje mediante repaso frecuente.

---

## ⛔ Restricciones

- El contenido depende de la interacción del usuario para generarse.
- Inicialmente solo se contará con una modalidad de juego (modo single).

---

## ✅ SCRUM - Convenciones

### Definition of Ready (DoR)

- Tareas sin dependencias bloqueantes.
- Criterios de aceptación establecidos.
- Alcance claro y descripción completa.
- Tareas asignadas y completamente estimadas.

### Definition of Done (DoD)

- Código revisado y funcional.
- Criterios de aceptación validados.
- Revisión por parte del PR reviewer.
- Merge a la rama `develop`.

---

## 👨‍💻 Equipo

- Barneche Facundo
- Menini Alejo
- Gibelli Julian
- Guarino Alan
- Caceffo Juan
- Serafini Federico
- Sacchi Facundo

### Profesores:

- Pablo Andrés Núñez Monzon
- Mariano Cristobo


### 💻 Ejecutar en desarrollo

- Clonar el repositorio
- Levantar la base no relacional ```docker-compose -f docker-compose.yml -f docker-compose-dev.yml up -d```
- Levantar la aplicacion desde el IDE.

---

> Proyecto desarrollado en el marco de la materia **Proyecto de Software – TPI** en la **Universidad Nacional de San Martín (UNSAM)**
