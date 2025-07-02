package com.example.proyecto2025_BE.controller;

import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.client.payment.PaymentClient;
import com.mercadopago.resources.payment.Payment;

import lombok.RequiredArgsConstructor;

import com.example.proyecto2025_BE.service.MercadoPagoSubscriptionService;
import com.example.proyecto2025_BE.service.UserService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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
            // Mercado Pago envía el id del pago en el campo "data.id" y el tipo de notificación en "type"
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

    @GetMapping("/payment/verify")
    public ResponseEntity<?> verifyPayment(@RequestParam String payment_id) {
        try {
            PaymentClient paymentClient = new PaymentClient();
            Payment payment = paymentClient.get(Long.parseLong(payment_id));
            
            return ResponseEntity.ok(Map.of(
                "payment_id", payment.getId(),
                "status", payment.getStatus(),
                "email", payment.getPayer().getEmail(),
                "amount", payment.getTransactionAmount()
            ));
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error verificando pago: " + e.getMessage());
        }
    }

} 