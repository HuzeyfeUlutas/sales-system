package com.hulutas.inventory_service.service.impl;

import com.hulutas.inventory_service.dto.events.StockDepletedEvent;
import com.hulutas.inventory_service.dto.requests.CreateOrUpdateRequest;
import com.hulutas.inventory_service.dto.response.InventoryResponse;
import com.hulutas.inventory_service.exception.InventoryAlreadyExistsException;
import com.hulutas.inventory_service.exception.InventoryNotFoundException;
import com.hulutas.inventory_service.mapper.InventoryMapper;
import com.hulutas.inventory_service.model.Inventory;
import com.hulutas.inventory_service.repository.InventoryRepository;
import com.hulutas.inventory_service.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.hulutas.inventory_service.config.RabbitMQConfig.STOCK_DEPLETED_EXCHANGE;
import static com.hulutas.inventory_service.config.RabbitMQConfig.STOCK_DEPLETED_ROUTING_KEY;


@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {
    private final InventoryRepository inventoryRepository;
    private final InventoryMapper inventoryMapper;
    private final RabbitTemplate rabbitTemplate;

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
        product.setUnlimited(createOrUpdateRequest.unlimited());

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

    @Override
    @Transactional
    public boolean stockUpdate(String productCode, int quantity) {
        Inventory product = inventoryRepository.findByProductCode(productCode).orElseThrow(() -> new InventoryNotFoundException("Product with code " + productCode + " not found in inventory"));

        if(product.isUnlimited())
            return true;

        if (product.getStockQuantity() < quantity)
            return false;

        product.setStockQuantity(product.getStockQuantity() - quantity);
        inventoryRepository.save(product);
        try {
            if (product.getStockQuantity() == 0){
                rabbitTemplate.convertAndSend(STOCK_DEPLETED_EXCHANGE,
                        STOCK_DEPLETED_ROUTING_KEY,
                        new StockDepletedEvent(productCode, 0, "Test"));
            }

        } catch (Exception e){
            throw new RuntimeException("Failed to send message via RabbitMQ");
        }

        return true;
    }
}
