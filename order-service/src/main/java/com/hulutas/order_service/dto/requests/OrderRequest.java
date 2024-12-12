package com.hulutas.order_service.dto.requests;

import com.hulutas.order_service.enums.PaymentType;

import java.math.BigDecimal;
import java.util.List;

public record OrderRequest (
        List<OrderItemRequest> items,
        PaymentType paymentMethod,
        BigDecimal totalAmount
) {
}
