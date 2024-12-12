package com.hulutas.payment_service.service.impl;

import com.hulutas.payment_service.dto.requests.OrderPaymentRequest;
import com.hulutas.payment_service.enums.PaymentStatus;
import com.hulutas.payment_service.model.Payment;
import com.hulutas.payment_service.repository.PaymentRepository;
import com.hulutas.payment_service.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository paymentRepository;
    @Override
    public Payment processPayment(OrderPaymentRequest request) {
        Payment payment = Payment.builder()
                .orderId(request.orderId())
                .amount(request.amount())
                .paymentType(request.paymentType())
                .paymentStatus(PaymentStatus.PENDING)
                .paymentDate(LocalDateTime.now())
                .build();

        //Switch for payment types
        switch (request.paymentType()) {
            case EFT -> processEftPayment(payment);
            case CREDIT_CARD -> processCreditCardPayment(payment);
            case CASH -> payment.setPaymentStatus(PaymentStatus.SUCCESS);
        }

        return paymentRepository.save(payment);
    }

    private void processCreditCardPayment(Payment payment) {
        // Mock credit card process
        boolean success = mockCreditCardProcessing(payment.getAmount());
        payment.setPaymentStatus(success ? PaymentStatus.SUCCESS : PaymentStatus.FAILED);
    }

    private void processEftPayment(Payment payment) {
        // Mock eft process
        boolean success = mockEftProcessing(payment.getAmount());
        payment.setPaymentStatus(success ? PaymentStatus.SUCCESS : PaymentStatus.FAILED);
    }

    private boolean mockCreditCardProcessing(BigDecimal amount) {
        return true;
    }

    private boolean mockEftProcessing(BigDecimal amount) {
        return false;
    }
}
