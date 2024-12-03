package com.hulutas.inventory_service.service;

import com.hulutas.inventory_service.dto.requests.CreateOrUpdateRequest;
import com.hulutas.inventory_service.dto.response.InventoryResponse;

import java.util.List;

public interface InventoryService {
    List<InventoryResponse> getAll();
    InventoryResponse getByProductCode(String productCode);
    InventoryResponse createInventory(CreateOrUpdateRequest createOrUpdateRequest);
    InventoryResponse updateInventory(CreateOrUpdateRequest createOrUpdateRequest);
    InventoryResponse deleteInventory(String productCode);
}
