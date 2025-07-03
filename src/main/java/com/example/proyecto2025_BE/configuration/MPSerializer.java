package com.example.proyecto2025_BE.configuration;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;

public final class MPSerializer {
	
	private static ObjectMapper mapper;

	private MPSerializer() { }
	
	public static ObjectMapper instance() {
		if(mapper == null) {
			mapper = new ObjectMapper();
			mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
			mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			mapper.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
		}
		
		return mapper;
	}
}
