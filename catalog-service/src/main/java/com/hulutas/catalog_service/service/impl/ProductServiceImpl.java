package com.hulutas.catalog_service.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import com.hulutas.catalog_service.dto.requests.CreateOrUpdateRequest;
import com.hulutas.catalog_service.dto.requests.Filter;
import com.hulutas.catalog_service.dto.responses.ProductResponse;
import com.hulutas.catalog_service.exception.ProductAlreadyExistsException;
import com.hulutas.catalog_service.exception.ProductNotFoundException;
import com.hulutas.catalog_service.mapper.ProductMapper;
import com.hulutas.catalog_service.model.Product;
import com.hulutas.catalog_service.repository.ProductRepository;
import com.hulutas.catalog_service.service.ProductService;
import com.hulutas.catalog_service.specification.ProductSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Override
    @Cacheable(value = "products", key = "'all_products'")
    public List<ProductResponse> getAll(Filter filter) {
        List<Product> products = productRepository.findAll(ProductSpecification.getProductSpecifications(filter));

        if(products.isEmpty())
            throw new ProductNotFoundException("No products found in the catalog with filtered");

        return products.stream().map(productMapper :: toDto).toList();
    }

    @Override
    public ProductResponse getByProductCode(String productCode) {
        Product product = productRepository.findByCode(productCode).orElseThrow(() -> new ProductNotFoundException("Product with code " + productCode + " not found"));


        return productMapper.toDto(product);
    }

    @Override
    @CacheEvict(value = "products", key = "'all_products'")
    public ProductResponse createProduct(CreateOrUpdateRequest createOrUpdateRequest) {
        if (productRepository.findByCode(createOrUpdateRequest.code()).isPresent()) {
            throw new ProductAlreadyExistsException("Product with code " + createOrUpdateRequest.code() + " already exists.");
        }

        Product product = productMapper.toEntity(createOrUpdateRequest);
        Product response = productRepository.save(product);


        return productMapper.toDto(response);
    }

    @Override
    @CacheEvict(value = "products", key = "'all_products'")
    public ProductResponse updateProduct(CreateOrUpdateRequest createOrUpdateRequest) {
        if (productRepository.findByCode(createOrUpdateRequest.code()).isEmpty()) {
            throw new ProductNotFoundException("Product with code " + createOrUpdateRequest.code() + " not found.");
        }
        Product product = productMapper.toEntity(createOrUpdateRequest);
        Product response = productRepository.save(product);

        return productMapper.toDto(response);
    }

    @Override
    @CacheEvict(value = "products", key = "'all_products'")
    public ProductResponse deleteProduct(String productCode) {
        Product product = productRepository.findByCode(productCode).orElseThrow(() -> new ProductNotFoundException("Product with code " + productCode + " not found"));

        product.setDeleted(true);

        Product response = productRepository.save(product);


        return productMapper.toDto(response);
    }
}
