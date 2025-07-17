package com.example.proyecto2025_BE.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class InternalServerErrorException extends RuntimeException {
	
	private static final long serialVersionUID = 54567123456876345L;

	public InternalServerErrorException(String message) {
		super(message);
	}
	
	public static InternalServerErrorException build(String message) {
		return new InternalServerErrorException(message);
	}
}
