package com.hulutas.catalog_service.dto.requests;

import jakarta.validation.constraints.NotNull;

public record CreateOrUpdateRequest(
        @NotNull
        String name,
        @NotNull
        String description,
        @NotNull
        String code,
        @NotNull
        boolean hasStock,
        int stock,
        @NotNull
        double price
) {
}
