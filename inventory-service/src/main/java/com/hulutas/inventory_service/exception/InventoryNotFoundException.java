package com.hulutas.inventory_service.exception;

public class InventoryNotFoundException extends RuntimeException{
    public InventoryNotFoundException(String message) {
        super(message);
    }
}
