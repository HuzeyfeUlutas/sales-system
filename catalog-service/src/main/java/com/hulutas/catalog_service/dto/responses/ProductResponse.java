package com.hulutas.catalog_service.dto.responses;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@class")
public record ProductResponse(
        String name,
        String description,
        String code,
        boolean stockStatus,
        double price
) {
}