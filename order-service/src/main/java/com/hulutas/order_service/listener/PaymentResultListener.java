package com.hulutas.order_service.listener;

import com.hulutas.order_service.model.Order;
import com.hulutas.order_service.repository.OrderRepository;
import com.hulutas.order_service.saga.OrderSagaOrchestrator;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import com.hulutas.order_service.events.PaymentEvents.*;

import java.util.UUID;

import static com.hulutas.order_service.config.RabbitMQConfig.PAYMENT_RESULT_FAILURE_QUEUE;
import static com.hulutas.order_service.config.RabbitMQConfig.PAYMENT_RESULT_SUCCESS_QUEUE;

@Component
@RequiredArgsConstructor
public class PaymentResultListener {
    private final OrderRepository orderRepository;
    private final OrderSagaOrchestrator orderSagaOrchestrator;

    @RabbitListener(queues = PAYMENT_RESULT_SUCCESS_QUEUE)
    public void handlePaymentCompletedEvent(PaymentSuccessEvent event) throws Exception {
        try {
            System.out.println("Payment completed event received: " + event);

            Order order = orderRepository.findById(UUID.fromString(event.getOrderId()))
                    .orElseThrow(() -> new RuntimeException("Order not found"));
            orderSagaOrchestrator.completeOrder(order);
        } catch (Exception e){
            throw new Exception("Payment complete error");
        }
    }

    @RabbitListener(queues = PAYMENT_RESULT_FAILURE_QUEUE)
    public void handlePaymentFailedEvent(PaymentFailureEvent event) throws Exception {
        try {
            System.out.println("Payment failed event received: " + event);

            Order order = orderRepository.findById(UUID.fromString(event.getOrderId()))
                    .orElseThrow(() -> new RuntimeException("Order not found"));
            orderSagaOrchestrator.failOrder(order, "Payment failed");
        } catch (Exception e){
            throw new Exception("Payment complete error");
        }

    }

}
