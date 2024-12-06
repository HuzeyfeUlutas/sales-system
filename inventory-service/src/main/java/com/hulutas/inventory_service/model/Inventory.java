package com.hulutas.inventory_service.model;

import com.hulutas.inventory_service.model.base.BaseEntity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "inventory")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Inventory extends BaseEntity {
    @NotBlank(message = "Product name cannot be empty")
    @Indexed(unique = true)
    private String productCode;
    @PositiveOrZero(message = "Stock quantity must be positive")
    private int stockQuantity;
    private boolean unlimited;
}
