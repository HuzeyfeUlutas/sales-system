package com.hulutas.catalog_service.grpc;


public interface InventoryClientService {
    boolean checkStockStatus(String productCode) throws InterruptedException;
    boolean updateStockStatus(String productCode, int quantity, boolean unlimited) throws InterruptedException;
    boolean deleteStockStatus(String productCode, int quantity) throws InterruptedException;
    boolean saveStockStatus(String productCode, int quantity, boolean unlimited) throws InterruptedException;
}
