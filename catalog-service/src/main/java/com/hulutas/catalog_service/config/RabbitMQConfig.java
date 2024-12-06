package com.hulutas.catalog_service.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String STOCK_DEPLETED_QUEUE = "stock.depleted.queue";
    public static final String STOCK_DEPLETED_EXCHANGE = "stock.depleted.exchange";
    public static final String STOCK_DEPLETED_ROUTING_KEY = "stock.depleted.routing.key";

    @Bean
    public Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public Queue stockDepletedQueue() {
        return new Queue(STOCK_DEPLETED_QUEUE);
    }

    @Bean
    public DirectExchange stockDepletedExchange() {
        return new DirectExchange(STOCK_DEPLETED_EXCHANGE);
    }

    @Bean
    public Binding stockDepletedBinding(Queue stockDepletedQueue, DirectExchange stockDepletedExchange) {
        return BindingBuilder.bind(stockDepletedQueue).to(stockDepletedExchange).with(STOCK_DEPLETED_ROUTING_KEY);
    }
}
