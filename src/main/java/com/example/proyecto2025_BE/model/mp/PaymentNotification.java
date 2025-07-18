package com.example.proyecto2025_BE.model.mp;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@lombok.Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentNotification {
	
	private Data data;
	private LocalDateTime dateCreated;
	private String type;
	
	public Long id() {
		return Long.parseLong(data.getId());
	}
}