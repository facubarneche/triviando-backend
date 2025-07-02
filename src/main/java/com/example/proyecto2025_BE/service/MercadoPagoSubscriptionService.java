package com.example.proyecto2025_BE.service;

import java.math.BigDecimal;
import java.util.Collections;

import org.springframework.stereotype.Service;

import com.mercadopago.client.preference.PreferenceBackUrlsRequest;
import com.mercadopago.client.preference.PreferenceClient;
import com.mercadopago.client.preference.PreferenceItemRequest;
import com.mercadopago.client.preference.PreferenceRequest;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.preference.Preference;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MercadoPagoSubscriptionService {

    private static final String PLAN_TITLE = "Suscripción mensual TrivIAndo";
    private static final String PLAN_DESCRIPTION = "Acceso a funcionalidades premium de la aplicación";
    private static final String PLAN_CURRENCY = "ARS";
    private static final BigDecimal PLAN_PRICE = BigDecimal.valueOf(100);
    private static final String WEBHOOK_BASE_URL = "https://localhost:3000/payment";
    
    public String createSubscriptionPreference(String userEmail) throws MPException, MPApiException {
        PreferenceClient client = new PreferenceClient();

        PreferenceItemRequest itemRequest = PreferenceItemRequest.builder()
                .title(PLAN_TITLE)
                .description(PLAN_DESCRIPTION)
                .quantity(1)
                .currencyId(PLAN_CURRENCY)
                .unitPrice(PLAN_PRICE)
                .build();

        PreferenceBackUrlsRequest backUrls = PreferenceBackUrlsRequest.builder()
                .success(WEBHOOK_BASE_URL + "/success")
                .failure(WEBHOOK_BASE_URL + "/failure")
                .pending(WEBHOOK_BASE_URL + "/pending")
                .build();

        PreferenceRequest preferenceRequest = PreferenceRequest.builder()
                .items(Collections.singletonList(itemRequest))
                .backUrls(backUrls)
                .autoReturn("approved")
                .build();

        Preference preference = client.create(preferenceRequest);
        
        return preference.getInitPoint();
    }
} 