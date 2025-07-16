package com.example.proyecto2025_BE.configuration;

import com.example.proyecto2025_BE.model.mp.PaymentNotification;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MPSerializerTest {

    @Test
    void shouldDeserializePaymentNotificationWithLocalDateTime() throws Exception {
        // Given
        String jsonPayload = """
            {
                "action":"payment.created",
                "api_version":"v1",
                "data":{
                    "id":"118616338480"
                },
                "date_created":"2025-07-15T00:16:02Z",
                "id":122988273118,
                "live_mode":true,
                "type":"payment",
                "user_id":"2523942300"
            }
            """;

        // When
        PaymentNotification notification = MPSerializer.instance()
                .readValue(jsonPayload, PaymentNotification.class);

        // Then
        assertNotNull(notification);
        assertNotNull(notification.getDateCreated());
        assertEquals("payment", notification.getType());
        assertNotNull(notification.getData());
        assertEquals("118616338480", notification.getData().getId());
        assertEquals(118616338480L, notification.id());
    }
} 