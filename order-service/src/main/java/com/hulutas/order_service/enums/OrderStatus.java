package com.hulutas.order_service.enums;

public enum OrderStatus {
    CREATED,
    PENDING_PAYMENT,
    PAYMENT_SUCCESS,
    PAYMENT_FAILED,
    COMPLETED,
    CANCELED
}
