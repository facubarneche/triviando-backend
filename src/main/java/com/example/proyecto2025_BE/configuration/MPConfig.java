package com.example.proyecto2025_BE.configuration;

import org.springframework.context.annotation.Configuration;

import com.mercadopago.MercadoPagoConfig;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

@Configuration
public class MPConfig {

	@Value("${mercadopago.access-token}")
    private String accessToken;
	
	@Bean
    public MercadoPagoConfig mercadoPagoConfig() {
        MercadoPagoConfig.setAccessToken(accessToken);
        
        return new MercadoPagoConfig();
    }
}
