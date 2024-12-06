package com.hulutas.inventory_service.grpc;

import com.hulutas.InventoryServiceGrpc;
import com.hulutas.StockRequest;
import com.hulutas.StockResponse;
import com.hulutas.StockSaveOrUpdateResponse;
import com.hulutas.inventory_service.exception.InventoryAlreadyExistsException;
import com.hulutas.inventory_service.exception.InventoryNotFoundException;
import com.hulutas.inventory_service.model.Inventory;
import com.hulutas.inventory_service.repository.InventoryRepository;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import lombok.AllArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
@AllArgsConstructor
public class InventoryGrpcService extends InventoryServiceGrpc.InventoryServiceImplBase {

    private final InventoryRepository inventoryRepository;

    @Override
    public void updateStock(StockRequest request, StreamObserver<StockSaveOrUpdateResponse> responseObserver) {
        try {
            Inventory product = inventoryRepository.findByProductCode(request.getProductCode()).orElseThrow(() -> new InventoryNotFoundException("Product with code " + request.getProductCode() + " not found in inventory"));

            product.setStockQuantity(request.getQuantity());

            inventoryRepository.save(product);
            StockSaveOrUpdateResponse response = StockSaveOrUpdateResponse.newBuilder()
                    .setSuccess(true)
                    .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }catch (InventoryNotFoundException ex) {
            responseObserver.onError(io.grpc.Status.NOT_FOUND
                    .withDescription(ex.getMessage())
                    .asRuntimeException());
        }catch (Exception ex) {
            responseObserver.onError(io.grpc.Status.INTERNAL
                    .withDescription("An unexpected error occurred")
                    .asRuntimeException());
        }
    }

    @Override
    public void saveStock(StockRequest request, StreamObserver<StockSaveOrUpdateResponse> responseObserver) {
        try {
            if (inventoryRepository.findByProductCode(request.getProductCode()).isPresent())
                throw new InventoryAlreadyExistsException("Product with code " + request.getProductCode() + " already exists in inventory");

            Inventory product = new Inventory(request.getProductCode(), request.getQuantity(), request.getUnlimited());
            inventoryRepository.save(product);

            StockSaveOrUpdateResponse response = StockSaveOrUpdateResponse.newBuilder()
                    .setSuccess(true)
                    .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }catch (InventoryAlreadyExistsException ex) {
            responseObserver.onError(Status.ALREADY_EXISTS
                    .withDescription(ex.getMessage())
                    .asRuntimeException());
        }catch (Exception ex) {
            responseObserver.onError(io.grpc.Status.INTERNAL
                    .withDescription("An unexpected error occurred")
                    .asRuntimeException());
        }
    }

    @Override
    public void checkStock(StockRequest request, StreamObserver<StockResponse> responseObserver) {
        try {
            Inventory product = inventoryRepository.findByProductCode(request.getProductCode()).orElseThrow(() -> new InventoryNotFoundException("Product with code " + request.getProductCode() + " not found in inventory"));

            StockResponse response = StockResponse.newBuilder()
                    .setIsAvailable(product.getStockQuantity() > 0 || product.isUnlimited())
                    .build();


            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }catch (InventoryNotFoundException ex) {
            responseObserver.onError(io.grpc.Status.NOT_FOUND
                    .withDescription(ex.getMessage())
                    .asRuntimeException());
        }catch (Exception ex) {
            responseObserver.onError(io.grpc.Status.INTERNAL
                    .withDescription("An unexpected error occurred")
                    .asRuntimeException());
        }
    }

    @Override
    public void deleteStock(StockRequest request, StreamObserver<StockSaveOrUpdateResponse> responseObserver) {
        try {
            Inventory product = inventoryRepository.findByProductCode(request.getProductCode()).orElseThrow(() -> new InventoryNotFoundException("Product with code " + request.getProductCode() + " not found in inventory"));

            product.setDeleted(true);
            inventoryRepository.save(product);

            StockSaveOrUpdateResponse response = StockSaveOrUpdateResponse.newBuilder()
                    .setSuccess(true)
                    .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }catch (InventoryNotFoundException ex) {
            responseObserver.onError(io.grpc.Status.NOT_FOUND
                    .withDescription(ex.getMessage())
                    .asRuntimeException());
        }catch (Exception ex) {
            responseObserver.onError(io.grpc.Status.INTERNAL
                    .withDescription("An unexpected error occurred")
                    .asRuntimeException());
        }
    }
}
