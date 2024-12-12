package com.hulutas.order_service.grpc.impl;

import com.hulutas.InventoryServiceGrpc;
import com.hulutas.StockRequest;
import com.hulutas.StockResponse;
import com.hulutas.StockSaveOrUpdateResponse;
import com.hulutas.order_service.grpc.StockClient;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

@Service
public class StockClientImpl implements StockClient {
    @GrpcClient("stockService")
    InventoryServiceGrpc.InventoryServiceBlockingStub synchClient;

    @Override
    public boolean checkStockStatus(String productCode) {
        StockRequest request = StockRequest.newBuilder()
                .setProductCode(productCode).build();

        StockResponse response = synchClient.checkStock(request);

        return response.getIsAvailable();
    }

    @Override
    public StockSaveOrUpdateResponse orderStock(String productCode, int quantity) {
        StockRequest request = StockRequest.newBuilder()
                .setQuantity(quantity)
                .setProductCode(productCode)
                .build();

        return synchClient.orderStock(request);
    }

    @Override
    public StockSaveOrUpdateResponse rolebackStock(String productCode, int quantity) {
        StockRequest request = StockRequest.newBuilder()
                .setQuantity(quantity)
                .setProductCode(productCode)
                .build();

        return synchClient.rolebackStock(request);
    }
}
