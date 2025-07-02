package com.example.proyecto2025_BE.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.mercadopago.MercadoPagoConfig;

import lombok.Data;

@Data
@Configuration
@ConfigurationProperties(prefix = "mercadopago")
public class MPConfig {

    private String accessToken;
	
	@Bean
    public MercadoPagoConfig mercadoPagoConfig() {
        MercadoPagoConfig.setAccessToken(accessToken);
        
        return new MercadoPagoConfig();
    }
}
