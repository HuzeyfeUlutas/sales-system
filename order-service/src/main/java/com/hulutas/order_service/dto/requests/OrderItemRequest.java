package com.hulutas.order_service.dto.requests;

import java.math.BigDecimal;
import java.util.UUID;

public record OrderItemRequest(
        String productId,
        int quantity,
        BigDecimal amount
) {
}
