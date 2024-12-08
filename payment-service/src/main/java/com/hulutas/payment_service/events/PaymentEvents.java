package com.hulutas.payment_service.events;

import com.hulutas.payment_service.enums.PaymentType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

public class PaymentEvents {
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PaymentSuccessEvent {
        private String orderId;
        private BigDecimal amount;
        @Enumerated(EnumType.STRING)
        private PaymentType paymentType;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PaymentFailureEvent {
        private String orderId;
        private String reason;
    }
}
