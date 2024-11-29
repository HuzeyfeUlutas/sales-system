package com.hulutas.catalog_service.exception;

public class ProductAlreadyExistsException extends RuntimeException{

    public ProductAlreadyExistsException(String message) {
        super(message);
    }
}
