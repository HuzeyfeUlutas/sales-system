package com.hulutas.order_service.dto.responses;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record OrderResponse(
        UUID orderId,
        String status,
        String message,
        BigDecimal totalAmount,
        List<OrderItemResponse> items
) {
}
