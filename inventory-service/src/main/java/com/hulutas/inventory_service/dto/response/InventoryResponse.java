package com.hulutas.inventory_service.dto.response;

public record InventoryResponse(
        String productCode,
        int stockQuantity
) {
}
