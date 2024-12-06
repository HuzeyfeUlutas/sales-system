package com.hulutas.catalog_service.service.impl;

import com.hulutas.catalog_service.dto.events.StockDepletedEvent;
import com.hulutas.catalog_service.exception.ProductNotFoundException;
import com.hulutas.catalog_service.model.Product;
import com.hulutas.catalog_service.repository.ProductRepository;
import com.hulutas.catalog_service.service.StockEventListener;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import static com.hulutas.catalog_service.config.RabbitMQConfig.STOCK_DEPLETED_QUEUE;

@Service
@AllArgsConstructor
public class StockEventListenerImpl implements StockEventListener {
    private final ProductRepository productRepository;

    @Override
    @RabbitListener(queues = STOCK_DEPLETED_QUEUE)
    @CacheEvict(value = "products", key = "'all_products'")
    public void listen(StockDepletedEvent event) {
        Product product = productRepository.findByCode(event.getProductId()).orElseThrow(() -> new ProductNotFoundException("Product with code " + event.getProductId() + " not found"));
        product.setHasStock(false);
        productRepository.save(product);
    }
}
