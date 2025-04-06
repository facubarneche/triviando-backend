package com.example.proyecto2025_BE.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends ConflictException{

    public NotFoundException(String message) {
        super(message);
    }
}