package com.example.proyecto2025_BE.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

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

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGeneralExceptions(Exception ex) {
        log.error("Ocurrió una excepción no controlada: ", ex);
        Map<String, String> errors = new HashMap<>();
        errors.put("error", ex.getMessage() == null ? "Ocurrio un error inesperado." : ex.getMessage());
        ResponseStatus responseStatus = ex.getClass().getAnnotation(ResponseStatus.class);
        return new ResponseEntity<>(errors, responseStatus != null ? responseStatus.value() : HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
