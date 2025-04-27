package com.example.proyecto2025_BE.exceptions;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<Object> handleConflictException(ConflictException ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put("error", ex.getMessage());
        ResponseStatus responseStatus = ex.getClass().getAnnotation(ResponseStatus.class);
        HttpStatus status = responseStatus != null ? responseStatus.value() : HttpStatus.CONFLICT;
        return new ResponseEntity<>(errors, status);
    }

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<Object> handleGeneralExceptions(Exception ex) {
        log.error("Ocurrió una excepción no controlada: ", ex);
        Map<String, String> errors = new HashMap<>();
        errors.put("error", ex.getMessage() == null ? "Ocurrio un error inesperado." : ex.getMessage());
        ResponseStatus responseStatus = ex.getClass().getAnnotation(ResponseStatus.class);
        return new ResponseEntity<>(errors, responseStatus != null ? responseStatus.value() : HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> handleNotFoundtException(NotFoundException ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put("error", ex.getMessage());
        ResponseStatus responseStatus = ex.getClass().getAnnotation(ResponseStatus.class);
        HttpStatus status = responseStatus != null ? responseStatus.value() : HttpStatus.NOT_FOUND;
        return new ResponseEntity<>(errors, status);
    }
}
