package com.hulutas.inventory_service.grpc;

import com.hulutas.InventoryServiceGrpc;
import com.hulutas.StockRequest;
import com.hulutas.StockResponse;
import com.hulutas.StockSaveOrUpdateResponse;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class InventoryGrpcService extends InventoryServiceGrpc.InventoryServiceImplBase {

    @Override
    public void updateStock(StockRequest request, StreamObserver<StockSaveOrUpdateResponse> responseObserver) {
        StockSaveOrUpdateResponse response = StockSaveOrUpdateResponse.newBuilder()
                .setSuccess(true)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void saveStock(StockRequest request, StreamObserver<StockSaveOrUpdateResponse> responseObserver) {
        StockSaveOrUpdateResponse response = StockSaveOrUpdateResponse.newBuilder()
                .setSuccess(true)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void checkStock(StockRequest request, StreamObserver<StockResponse> responseObserver) {
        StockResponse response = StockResponse.newBuilder()
                .setIsAvailable(true)
                .setMessage("OK")
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
