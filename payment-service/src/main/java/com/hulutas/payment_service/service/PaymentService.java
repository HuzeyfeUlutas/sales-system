package com.hulutas.payment_service.service;

import com.hulutas.payment_service.dto.requests.OrderPaymentRequest;
import com.hulutas.payment_service.model.Payment;

public interface PaymentService {
    Payment processPayment(OrderPaymentRequest request);
}
