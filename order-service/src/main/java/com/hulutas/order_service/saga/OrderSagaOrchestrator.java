package com.hulutas.order_service.saga;

import com.hulutas.order_service.model.Order;

public interface OrderSagaOrchestrator {
    void startSaga(Order order);
    void completeOrder(Order order);
    void failOrder(Order order, String reason);
    void decreaseStock(String productId, int quantity);
    void rolebackStock(String productId, int quantity);
    void startPaymentProcess(Order order);
}
