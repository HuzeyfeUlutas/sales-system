package com.hulutas.inventory_service.dto.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

public record CreateOrUpdateRequest(
        @NotBlank(message = "Product code cannot be empty")
        String productCode,
        @PositiveOrZero(message = "Stock quantity must be positive")
        int stockQuantity
) {
}
