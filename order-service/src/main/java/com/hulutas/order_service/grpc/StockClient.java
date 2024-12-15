package com.hulutas.order_service.grpc;

import com.hulutas.StockSaveOrUpdateResponse;

public interface StockClient {
    boolean checkStockStatus(String productCode, int quantity);
    StockSaveOrUpdateResponse orderStock(String productCode, int quantity);
    StockSaveOrUpdateResponse rolebackStock(String productCode, int quantity);
}
