package com.hulutas.order_service.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    public static final String ORDER_PAYMENT_QUEUE = "order.payment.queue";
    public static final String PAYMENT_RESULT_SUCCESS_QUEUE = "payment.result.success";
    public static final String PAYMENT_RESULT_FAILURE_QUEUE = "payment.result.failure";
    public static final String ORDER_PAYMENT_EXCHANGE = "payment.exchange";
    public static final String ORDER_PAYMENT_ROUTING_KEY = "order.payment.key";
    public static final String PAYMENT_RESULT_SUCCESS_ROUTING_KEY = "payment.result.success.key";

    public static final String PAYMENT_RESULT_FAILURE_ROUTING_KEY = "payment.result.failure.key";

    @Bean
    public Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public Queue orderPaymentQueue() {
        return new Queue(ORDER_PAYMENT_QUEUE, true);
    }
    @Bean
    public DirectExchange orderServiceExchange() {
        return new DirectExchange(ORDER_PAYMENT_EXCHANGE);
    }
    @Bean
    public Binding bindingOrderPayment(Queue orderPaymentQueue, DirectExchange orderServiceExchange) {
        return BindingBuilder.bind(orderPaymentQueue).to(orderServiceExchange).with(ORDER_PAYMENT_ROUTING_KEY);
    }
    @Bean
    public Queue paymentResultSuccessQueue() {
        return new Queue(PAYMENT_RESULT_SUCCESS_QUEUE, true);
    }
    @Bean
    public Queue paymentResultFailureQueue() {
        return new Queue(PAYMENT_RESULT_FAILURE_QUEUE, true);
    }
    @Bean
    public Binding bindingPaymentResultFailure(Queue paymentResultFailureQueue, DirectExchange orderServiceExchange) {
    return BindingBuilder.bind(paymentResultFailureQueue).to(orderServiceExchange).with(PAYMENT_RESULT_FAILURE_ROUTING_KEY);
    }
    @Bean
    public Binding bindingPaymentResultSuccess(Queue paymentResultSuccessQueue, DirectExchange orderServiceExchange) {
        return BindingBuilder.bind(paymentResultSuccessQueue).to(orderServiceExchange).with(PAYMENT_RESULT_SUCCESS_ROUTING_KEY);
    }

}
