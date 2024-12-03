package com.hulutas.inventory_service.controller;

import com.hulutas.inventory_service.dto.requests.CreateOrUpdateRequest;
import com.hulutas.inventory_service.dto.response.InventoryResponse;
import com.hulutas.inventory_service.service.InventoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("inventory/api/products")
@RequiredArgsConstructor
public class InventoryController {
    private final InventoryService inventoryService;

    @GetMapping()
    public List<InventoryResponse> getAll() {

        return inventoryService.getAll();
    }

    @GetMapping("/{productCode}")
    public InventoryResponse getByCode(@PathVariable String productCode){

        return inventoryService.getByProductCode(productCode);
    }

    @PostMapping()
    public InventoryResponse add(@Valid @RequestBody CreateOrUpdateRequest createOrUpdateRequest) {

        return inventoryService.createInventory(createOrUpdateRequest);
    }

    @PutMapping()
    public InventoryResponse update(@RequestBody CreateOrUpdateRequest createOrUpdateRequest) {

        return inventoryService.updateInventory(createOrUpdateRequest);
    }

    @DeleteMapping("/{productCode}")
    public InventoryResponse delete(@PathVariable String productCode) {

        return inventoryService.deleteInventory(productCode);
    }
}
