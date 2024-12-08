package com.hulutas.payment_service.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    public static final String ORDER_PAYMENT_QUEUE = "order.payment.queue";
    public static final String PAYMENT_RESULT_QUEUE = "payment.result.queue";
    public static final String ORDER_PAYMENT_EXCHANGE = "order.payment.exchange";
    public static final String ORDER_PAYMENT_ROUTING_KEY = "order.payment";
    public static final String PAYMENT_RESULT_ROUTING_KEY = "payment.result";

    @Bean
    public Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public Queue orderPaymentQueue() {
        return new Queue(ORDER_PAYMENT_QUEUE, true);
    }
    @Bean
    public TopicExchange orderServiceExchange() {
        return new TopicExchange(ORDER_PAYMENT_EXCHANGE);
    }
    @Bean
    public Binding bindingOrderPayment(Queue orderPaymentQueue, TopicExchange orderServiceExchange) {
        return BindingBuilder.bind(orderPaymentQueue).to(orderServiceExchange).with(ORDER_PAYMENT_ROUTING_KEY);
    }
    @Bean
    public Queue paymentResultQueue() {
        return new Queue(PAYMENT_RESULT_QUEUE, true);
    }
    @Bean
    public Binding bindingPaymentResult(Queue paymentResultQueue, TopicExchange orderServiceExchange) {
        return BindingBuilder.bind(paymentResultQueue).to(orderServiceExchange).with(PAYMENT_RESULT_ROUTING_KEY);
    }

}
