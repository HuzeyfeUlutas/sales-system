package com.hulutas.payment_service.dto.requests;

import com.hulutas.payment_service.enums.PaymentType;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record OrderPaymentRequest(
        @NotNull
        String orderId,
        @NotNull
        BigDecimal amount,
        @NotNull
        PaymentType paymentType

) {
}