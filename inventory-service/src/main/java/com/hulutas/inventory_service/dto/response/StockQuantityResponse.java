package com.hulutas.inventory_service.dto.response;

public record StockQuantityResponse(
        boolean stockStatus,
        String productCode
) {
}
