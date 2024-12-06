package com.hulutas.catalog_service.service;


import com.hulutas.catalog_service.dto.events.StockDepletedEvent;

public interface StockEventListener {
    void listen(StockDepletedEvent event);
}
