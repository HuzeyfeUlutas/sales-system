package com.hulutas.payment_service.events;

import com.hulutas.payment_service.enums.PaymentType;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;
import com.hulutas.payment_service.events.PaymentEvents.*;

import java.math.BigDecimal;

import static com.hulutas.payment_service.config.RabbitMQConfig.*;

@Component
@RequiredArgsConstructor
public class PaymentEventPublisher {
    private final RabbitTemplate rabbitTemplate;

    public void publishPaymentSuccessEvent(String orderId, BigDecimal amount, PaymentType paymentType) {
        PaymentSuccessEvent event = new PaymentSuccessEvent(orderId, amount, paymentType);
        rabbitTemplate.convertAndSend(ORDER_PAYMENT_EXCHANGE, PAYMENT_RESULT_SUCCESS_ROUTING_KEY , event);
    }

    public void publishPaymentFailureEvent(String orderId, String message) {
        PaymentFailureEvent event = new PaymentFailureEvent(orderId, message);
        rabbitTemplate.convertAndSend(ORDER_PAYMENT_EXCHANGE, PAYMENT_RESULT_FAILURE_ROUTING_KEY , event);
    }
}
