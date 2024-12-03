package com.hulutas.catalog_service.grpc;

import com.hulutas.InventoryServiceGrpc;
import com.hulutas.StockRequest;
import com.hulutas.StockResponse;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

@Service
public class InventoryClientService {

    @GrpcClient("stockService")
    InventoryServiceGrpc.InventoryServiceBlockingStub synchClient;

    @GrpcClient("stockService")
    InventoryServiceGrpc.InventoryServiceStub asynchClient;

    public boolean checkStockStatus() throws InterruptedException {
        StockRequest request = StockRequest.newBuilder()
                        .setProductCode("Test").setQuantity(12).build();


        StockResponse response = synchClient.checkStock(request);

        return response.getIsAvailable();
    }
}
