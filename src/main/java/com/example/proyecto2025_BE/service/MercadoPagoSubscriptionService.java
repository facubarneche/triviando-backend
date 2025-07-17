package com.example.proyecto2025_BE.service;

import java.util.Collections;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.proyecto2025_BE.configuration.MPSerializer;
import com.example.proyecto2025_BE.model.mp.PaymentNotification;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.mercadopago.client.payment.PaymentClient;
import com.mercadopago.client.preference.PreferenceBackUrlsRequest;
import com.mercadopago.client.preference.PreferenceClient;
import com.mercadopago.client.preference.PreferenceItemRequest;
import com.mercadopago.client.preference.PreferenceRequest;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.payment.Payment;
import com.mercadopago.resources.preference.Preference;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class MercadoPagoSubscriptionService {

	private final UserService userService;
    private final PreferenceClient preferenceClient;
    private final PaymentClient paymentClient;
    private final PreferenceItemRequest preferenceItemRequest;
    private final PreferenceBackUrlsRequest preferenceBackUrlsRequest;
    
    public String createSubscriptionPreference(String email) {
    	PreferenceRequest preferenceRequest = PreferenceRequest.builder()
                .items(Collections.singletonList(preferenceItemRequest))
                .backUrls(preferenceBackUrlsRequest)
                .autoReturn("approved")
                .externalReference(email)
                .build();
    	
    	Preference preference = null;
    	
		try {
			preference = preferenceClient.create(preferenceRequest);
		} catch (MPException e) {
			log.error("Error en el alta de la suscripción: MPException" + e.getMessage());
		} catch (MPApiException e) {
			log.error("Error en el alta de la suscripción: MPApiException" + e.getApiResponse().getContent());
		}
        
        return Optional.ofNullable(preference.getInitPoint()).orElse("");
    }
    
	public String notifyPayment(String payload) {
    	try {
        	PaymentNotification notification = MPSerializer.instance()
        			.readValue(payload, PaymentNotification.class);
        	
            Payment payment = paymentClient.get(notification.id());
            
            if ("approved".equalsIgnoreCase(payment.getStatus())) {
                String email = payment.getExternalReference();
                if (email != null) {
                    userService.markUserAsSubscribed(email);
                }
            }
        } catch (MPException | MPApiException e) {
        	log.error("Error procesando webhook: " + e.getMessage());
        } catch (JsonProcessingException e) {
        	log.error("Error parseando notification: " + e.getMessage());
        }
    	
    	return "Webhook procesado correctamente";
    }
} 