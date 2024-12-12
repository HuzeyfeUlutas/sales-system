package com.hulutas.order_service.service.impl;

import com.hulutas.order_service.dto.requests.OrderItemRequest;
import com.hulutas.order_service.dto.requests.OrderPaymentRequest;
import com.hulutas.order_service.dto.requests.OrderRequest;
import com.hulutas.order_service.dto.responses.OrderItemResponse;
import com.hulutas.order_service.dto.responses.OrderResponse;
import com.hulutas.order_service.enums.OrderStatus;
import com.hulutas.order_service.grpc.StockClient;
import com.hulutas.order_service.model.Order;
import com.hulutas.order_service.model.OrderItem;
import com.hulutas.order_service.repository.OrderRepository;
import com.hulutas.order_service.saga.OrderSagaOrchestrator;
import com.hulutas.order_service.service.OrderService;
import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.hulutas.order_service.config.RabbitMQConfig.ORDER_PAYMENT_EXCHANGE;
import static com.hulutas.order_service.config.RabbitMQConfig.ORDER_PAYMENT_ROUTING_KEY;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderSagaOrchestrator orderSagaOrchestrator;

    @Override
    public OrderResponse createOrder(OrderRequest orderRequest){

        Order order = Order.builder().status(OrderStatus.CREATED).totalAmount(orderRequest.totalAmount()).build();
        order.setItems(new ArrayList<>());

        for (OrderItemRequest itemRequest : orderRequest.items()){
            OrderItem item = OrderItem.builder()
                    .amount(itemRequest.amount())
                    .productId(itemRequest.productId())
                    .quantity(itemRequest.quantity())
                    .order(order)
                    .build();
            order.getItems().add(item);
        }

        orderRepository.save(order);

        List<OrderItemResponse> itemResponses = order.getItems().stream()
                .map(item -> new OrderItemResponse(item.getProductId(), item.getQuantity(), item.getAmount()))
                .toList();

        orderSagaOrchestrator.startSaga(order);

        return new OrderResponse(order.getId(), order.getStatus().name(), "Order created", orderRequest.totalAmount(), itemResponses);
    }

    @Override
    public OrderResponse getOrderById(UUID id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Order not found with ID: " + id));

        List<OrderItemResponse> itemResponses = order.getItems().stream()
                .map(item -> new OrderItemResponse(item.getProductId(), item.getQuantity(), item.getAmount()))
                .toList();

        return new OrderResponse(order.getId(), order.getStatus().name(), "Order retrieved successfully.", order.getTotalAmount(),itemResponses);
    }
}
