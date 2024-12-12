package com.hulutas.order_service.service;

import com.hulutas.order_service.dto.requests.OrderRequest;
import com.hulutas.order_service.dto.responses.OrderResponse;

import java.util.UUID;

public interface OrderService {
     OrderResponse createOrder(OrderRequest orderRequest);
     OrderResponse getOrderById(UUID id);
}
