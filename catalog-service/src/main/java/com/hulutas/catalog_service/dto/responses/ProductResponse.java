package com.hulutas.catalog_service.dto.responses;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, property = "@class")
@JsonIgnoreProperties(ignoreUnknown = true)
public record ProductResponse(
        String name,
        String description,
        String code,
        boolean hasStock,
        double price
) {
}
