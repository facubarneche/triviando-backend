#FROM maven:3.9.9-eclipse-temurin-21-alpine AS builder

# Copia el archivo JAR de tu aplicación al contenedor
#COPY target/*.jar app.jar

## Expone el puerto (usa un valor predeterminado)
#EXPOSE 8080

# Comando para ejecutar tu aplicación, usando la variable de entorno
#ENTRYPOINT ["java", "-jar", "-Dserver.port=$SERVER_PORT", "/app.jar"]