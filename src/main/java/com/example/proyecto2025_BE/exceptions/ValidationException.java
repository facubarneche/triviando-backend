package com.example.proyecto2025_BE.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ValidationException extends RuntimeException {
	
	private static final long serialVersionUID = 86435477775426153L;

	public ValidationException(String message) {
        super(message);
    }

    public static ValidationException build(String message) {
        return new ValidationException(message);
    }
}
