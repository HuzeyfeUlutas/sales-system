package com.hulutas.inventory_service.mapper;

import com.hulutas.inventory_service.dto.requests.CreateOrUpdateRequest;
import com.hulutas.inventory_service.dto.response.InventoryResponse;
import com.hulutas.inventory_service.model.Inventory;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface InventoryMapper {
    InventoryResponse toDto(Inventory inventory);

    Inventory toEntity(CreateOrUpdateRequest createOrUpdateRequest);
}
