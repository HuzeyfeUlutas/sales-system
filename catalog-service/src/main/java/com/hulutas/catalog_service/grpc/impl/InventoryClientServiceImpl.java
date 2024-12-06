package com.hulutas.catalog_service.grpc.impl;

import com.hulutas.InventoryServiceGrpc;
import com.hulutas.StockRequest;
import com.hulutas.StockResponse;
import com.hulutas.StockSaveOrUpdateResponse;
import com.hulutas.catalog_service.grpc.InventoryClientService;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

@Service
public class InventoryClientServiceImpl implements InventoryClientService {
    @GrpcClient("stockService")
    InventoryServiceGrpc.InventoryServiceBlockingStub synchClient;

/*    @GrpcClient("stockService")
    InventoryServiceGrpc.InventoryServiceStub asynchClient;*/

    @Override
    public boolean checkStockStatus(String productCode) throws InterruptedException {
        StockRequest request = StockRequest.newBuilder()
                .setProductCode(productCode).build();

        StockResponse response = synchClient.checkStock(request);

        return response.getIsAvailable();
    }

    @Override
    public boolean updateStockStatus(String productCode, int quantity, boolean unlimited) throws InterruptedException {
        StockRequest request = StockRequest.newBuilder()
                .setProductCode(productCode).setQuantity(quantity).setUnlimited(unlimited).build();

        StockSaveOrUpdateResponse response = synchClient.updateStock(request);

        return response.getSuccess();
    }

    @Override
    public boolean deleteStockStatus(String productCode, int quantity) throws InterruptedException {
        StockRequest request = StockRequest.newBuilder()
                .setProductCode(productCode).setQuantity(quantity).build();

        StockSaveOrUpdateResponse response = synchClient.deleteStock(request);

        return response.getSuccess();
    }

    @Override
    public boolean saveStockStatus(String productCode, int quantity, boolean unlimited) throws InterruptedException {
        StockRequest request = StockRequest.newBuilder()
                .setProductCode(productCode).setQuantity(quantity).setUnlimited(unlimited).build();

        StockSaveOrUpdateResponse response = synchClient.saveStock(request);

        return response.getSuccess();
    }
}
