package com.hulutas.inventory_service.service.impl;

import com.hulutas.inventory_service.dto.requests.CreateOrUpdateRequest;
import com.hulutas.inventory_service.dto.response.InventoryResponse;
import com.hulutas.inventory_service.exception.InventoryAlreadyExistsException;
import com.hulutas.inventory_service.exception.InventoryNotFoundException;
import com.hulutas.inventory_service.mapper.InventoryMapper;
import com.hulutas.inventory_service.model.Inventory;
import com.hulutas.inventory_service.repository.InventoryRepository;
import com.hulutas.inventory_service.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {
    private final InventoryRepository inventoryRepository;
    private final InventoryMapper inventoryMapper;

    @Override
    public List<InventoryResponse> getAll() {
        List<Inventory> products = inventoryRepository.findAll();

        if (products.isEmpty())
            throw new InventoryNotFoundException("No products found in inventory");

        return products.stream().map(inventoryMapper :: toDto).toList();
    }

    @Override
    public InventoryResponse getByProductCode(String productCode) {
        Inventory product = inventoryRepository.findByProductCode(productCode).orElseThrow(() -> new InventoryNotFoundException("Product with code " + productCode + " not found in inventory"));

        return inventoryMapper.toDto(product);
    }

    @Override
    public InventoryResponse createInventory(CreateOrUpdateRequest createOrUpdateRequest) {
        if (inventoryRepository.findByProductCode(createOrUpdateRequest.productCode()).isPresent())
            throw new InventoryAlreadyExistsException("Product with code " + createOrUpdateRequest.productCode() + " already exists in inventory");

        Inventory product = inventoryMapper.toEntity(createOrUpdateRequest);
        Inventory response = inventoryRepository.save(product);

        return inventoryMapper.toDto(response);
    }

    @Override
    public InventoryResponse updateInventory(CreateOrUpdateRequest createOrUpdateRequest) {
        Inventory product = inventoryRepository.findByProductCode(createOrUpdateRequest.productCode()).orElseThrow(() -> new InventoryNotFoundException("Product with code " + createOrUpdateRequest.productCode() + " not found in inventory"));


        product.setStockQuantity(createOrUpdateRequest.stockQuantity());

        Inventory response = inventoryRepository.save(product);

        return inventoryMapper.toDto(response);
    }

    @Override
    public InventoryResponse deleteInventory(String productCode) {
        Inventory product = inventoryRepository.findByProductCode(productCode).orElseThrow(() -> new InventoryNotFoundException("Product with code " + productCode + " not found in inventory"));

        product.setDeleted(true);

        Inventory response = inventoryRepository.save(product);
        return inventoryMapper.toDto(response);
    }
}
