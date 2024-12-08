package com.hulutas.payment_service.listener;

import com.hulutas.payment_service.dto.requests.OrderPaymentRequest;
import com.hulutas.payment_service.enums.PaymentStatus;
import com.hulutas.payment_service.events.PaymentEventPublisher;
import com.hulutas.payment_service.model.Payment;
import com.hulutas.payment_service.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import static com.hulutas.payment_service.config.RabbitMQConfig.ORDER_PAYMENT_QUEUE;

@Component
@RequiredArgsConstructor
public class PaymentListener {

    private final PaymentService paymentService;
    private final PaymentEventPublisher eventPublisher;

    @RabbitListener(queues = ORDER_PAYMENT_QUEUE)
    public void handleOrderPayment(OrderPaymentRequest request) {
        try {
            Payment payment = paymentService.processPayment(request);

            if (payment.getPaymentStatus() == PaymentStatus.SUCCESS) {
                eventPublisher.publishPaymentSuccessEvent(payment.getOrderId(), payment.getAmount(), payment.getPaymentType());
            } else {
                eventPublisher.publishPaymentFailureEvent(payment.getOrderId(), "Payment process fail!");
            }
        } catch (Exception e) {
            eventPublisher.publishPaymentFailureEvent(request.orderId(), e.getMessage());
        }
    }
}
