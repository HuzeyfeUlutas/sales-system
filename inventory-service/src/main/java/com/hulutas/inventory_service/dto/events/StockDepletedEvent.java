package com.hulutas.inventory_service.dto.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StockDepletedEvent {
    private String productId;
    private int quantity;
    private String orderId;
}
