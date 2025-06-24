package com.example.proyecto2025_BE.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class ConflictException extends RuntimeException {

	private static final long serialVersionUID = 161986165551L;

	
	private ConflictException(String message) {
		super(message);
	}
	
	public static ConflictException build(String message) {
		return new ConflictException(message);
	}
}
