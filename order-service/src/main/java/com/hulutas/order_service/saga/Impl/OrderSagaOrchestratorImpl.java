package com.hulutas.order_service.saga.Impl;

import com.hulutas.StockSaveOrUpdateResponse;
import com.hulutas.order_service.dto.requests.OrderItemRequest;
import com.hulutas.order_service.dto.requests.OrderPaymentRequest;
import com.hulutas.order_service.enums.OrderStatus;
import com.hulutas.order_service.enums.PaymentType;
import com.hulutas.order_service.grpc.StockClient;
import com.hulutas.order_service.model.Order;
import com.hulutas.order_service.model.OrderItem;
import com.hulutas.order_service.repository.OrderRepository;
import com.hulutas.order_service.saga.OrderSagaOrchestrator;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import static com.hulutas.order_service.config.RabbitMQConfig.ORDER_PAYMENT_EXCHANGE;
import static com.hulutas.order_service.config.RabbitMQConfig.ORDER_PAYMENT_ROUTING_KEY;

@Component
@RequiredArgsConstructor
public class OrderSagaOrchestratorImpl implements OrderSagaOrchestrator {
    private final RabbitTemplate rabbitTemplate;
    private final OrderRepository orderRepository;
    private final StockClient stockClient;
    @Override
    public void startSaga(Order order) {
        try {
            for (OrderItem item : order.getItems()) {
                boolean isAvailable = stockClient.checkStockStatus(item.getProductId());
                if (!isAvailable) {
                    throw new Exception("Insufficient stock");
                }
            }

            for (OrderItem item : order.getItems()){
                decreaseStock(item.getProductId(), item.getQuantity());
            }

            startPaymentProcess(order);
        }catch (Exception e){
            failOrder(order, e.getMessage());
        }

    }

    @Override
    public void completeOrder(Order order) {
        order.setStatus(OrderStatus.COMPLETED);
        orderRepository.save(order);
        System.out.println("Order completed successfully.");
    }

    @Override
    public void failOrder(Order order, String reason) {
        order.setStatus(OrderStatus.CANCELED);
        orderRepository.save(order);
        System.out.println("Order failed: " + reason);
    }

    @Override
    public void decreaseStock(String productId, int quantity) {
        StockSaveOrUpdateResponse response = stockClient.orderStock(productId, quantity);
        if (!response.getSuccess()) {
            throw new RuntimeException("Failed to decrease stock");
        }
    }

    @Override
    public void rolebackStock(String productId, int quantity) {
        StockSaveOrUpdateResponse response = stockClient.rolebackStock(productId, quantity);
        if (!response.getSuccess()) {
            throw new RuntimeException("Failed to role back stock");
        }
    }

    @Override
    public void startPaymentProcess(Order order) {
        OrderPaymentRequest paymentRequest = new OrderPaymentRequest(order.getId().toString(), order.getTotalAmount(), PaymentType.CREDIT_CARD);
        rabbitTemplate.convertAndSend(ORDER_PAYMENT_EXCHANGE, ORDER_PAYMENT_ROUTING_KEY, paymentRequest);

        order.setStatus(OrderStatus.PENDING_PAYMENT);
        orderRepository.save(order);
    }
}
