package com.example.proyecto2025_BE.configuration;

import java.math.BigDecimal;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.payment.PaymentClient;
import com.mercadopago.client.preference.PreferenceBackUrlsRequest;
import com.mercadopago.client.preference.PreferenceClient;
import com.mercadopago.client.preference.PreferenceItemRequest;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
@Configuration
@ConfigurationProperties(prefix = "mercadopago")
public class MPConfig {
	
	// TODO: Estos valores deberían ir por profile config + env vars
    private static final String PLAN_TITLE = "Suscripción mensual TrivIAndo";
    private static final String PLAN_DESCRIPTION = "Acceso a funcionalidades premium de la aplicación";
    private static final String PLAN_CURRENCY = "ARS";
    private static final BigDecimal PLAN_PRICE = BigDecimal.valueOf(100);
    private static final String FE_BASE_PATH = "/payment";

    private String feDomain;
    private String accessToken;
	
	@Bean
    public MercadoPagoConfig mercadoPagoConfig() {
		log.info("ACCESS TOKEN: {}", accessToken);
        MercadoPagoConfig.setAccessToken(accessToken);
        
        return new MercadoPagoConfig();
    }
	
	@Bean
	public PreferenceClient preferenceClient() {
		return new PreferenceClient();
	}
	
	@Bean
	public PaymentClient paymentClient() {
		return new PaymentClient();
	}
	
	@Bean
	public PreferenceItemRequest preferenceItemRequest() {
		return PreferenceItemRequest.builder()
                .title(PLAN_TITLE)
                .description(PLAN_DESCRIPTION)
                .quantity(1)
                .currencyId(PLAN_CURRENCY)
                .unitPrice(PLAN_PRICE)
                .build();
	}
	
	@Bean
	public PreferenceBackUrlsRequest preferenceBackUrlsRequest() {
        return PreferenceBackUrlsRequest.builder()
                .success(baseUrl() + "/success")
                .failure(baseUrl() + "/failure")
                .pending(baseUrl() + "/pending")
                .build();
	}
	
	private String baseUrl() {
		return feDomain + FE_BASE_PATH;
	}
}
