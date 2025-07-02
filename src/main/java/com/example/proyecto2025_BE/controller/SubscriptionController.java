package com.example.proyecto2025_BE.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.proyecto2025_BE.service.MercadoPagoSubscriptionService;
import com.example.proyecto2025_BE.service.UserService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mercadopago.client.payment.PaymentClient;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.payment.Payment;

import lombok.RequiredArgsConstructor;

@RestController
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class SubscriptionController {

    private final MercadoPagoSubscriptionService mpService;
    private final UserService userService;

    @PostMapping("/start")
    public ResponseEntity<?> startSubscription(@RequestParam String email) {
        try {
            String initPoint = mpService.createSubscriptionPreference(email);
            return ResponseEntity.ok().body(initPoint);
        } catch (MPException | MPApiException e) {
            return ResponseEntity.status(500).body("Error en el alta de la suscripción: " + e.getMessage());
        }
    }

    @PostMapping("/notifications")
    public ResponseEntity<String> mercadoPagoWebhook(@RequestBody String payload) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(payload);
            String type = root.path("type").asText();
            if ("payment".equals(type)) {
                Long paymentId = root.path("data").path("id").asLong();
                if (paymentId != null && paymentId > 0) {
                    PaymentClient paymentClient = new PaymentClient();
                    Payment payment = paymentClient.get(paymentId);
                    if ("approved".equalsIgnoreCase(payment.getStatus())) {
                        String email = payment.getPayer().getEmail();
                        if (email != null) {
                            userService.markUserAsSubscribed(email);
                        }
                    }
                }
            }
            return ResponseEntity.ok("Webhook procesado correctamente");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error procesando webhook: " + e.getMessage());
        }
    }
} 