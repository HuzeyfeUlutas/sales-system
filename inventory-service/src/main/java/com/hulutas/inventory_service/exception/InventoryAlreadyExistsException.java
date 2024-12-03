package com.hulutas.inventory_service.exception;

public class InventoryAlreadyExistsException extends RuntimeException{

    public InventoryAlreadyExistsException(String message) {
        super(message);
    }
}

