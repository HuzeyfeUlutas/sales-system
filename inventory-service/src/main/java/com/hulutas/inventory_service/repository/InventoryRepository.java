package com.hulutas.inventory_service.repository;

import com.hulutas.inventory_service.model.Inventory;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface InventoryRepository extends MongoRepository<Inventory, String> {
    Optional<Inventory> findByProductCode(String productCode);
}
