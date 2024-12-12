package com.hulutas.order_service.dto.responses;

import java.math.BigDecimal;
import java.util.UUID;

public record OrderItemResponse(
        String productId,
        int quantity,
        BigDecimal amount
) {
}
