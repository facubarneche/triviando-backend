package com.example.proyecto2025_BE.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.proyecto2025_BE.configuration.MPSerializer;
import com.example.proyecto2025_BE.model.mp.PaymentNotification;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.mercadopago.client.payment.PaymentClient;
import com.mercadopago.client.preference.PreferenceClient;
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
    private final PreferenceRequest preferenceRequest;
    
    public String createSubscriptionPreference() {
    	Preference preference = null;
		try {
			preference = preferenceClient.create(preferenceRequest);
		} catch (MPException | MPApiException e) {
			log.error("Error en el alta de la suscripción: " + e.getMessage());
		}
        
        return Optional.ofNullable(preference.getInitPoint()).orElse("");
    }
    
    public String notifyPayment(String payload) {
    	try {
        	PaymentNotification notification = MPSerializer.instance()
        			.readValue(payload, PaymentNotification.class);
        	
            Payment payment = paymentClient.get(notification.id());
            
            if ("approved".equalsIgnoreCase(payment.getStatus())) {
                String email = payment.getPayer().getEmail();
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