package com.hulutas.catalog_service.service.impl;

import com.hulutas.catalog_service.dto.requests.CreateOrUpdateRequest;
import com.hulutas.catalog_service.dto.requests.Filter;
import com.hulutas.catalog_service.dto.responses.ProductResponse;
import com.hulutas.catalog_service.exception.ProductAlreadyExistsException;
import com.hulutas.catalog_service.exception.ProductNotFoundException;
import com.hulutas.catalog_service.grpc.InventoryClientService;
import com.hulutas.catalog_service.mapper.ProductMapper;
import com.hulutas.catalog_service.model.Product;
import com.hulutas.catalog_service.repository.ProductRepository;
import com.hulutas.catalog_service.service.ProductService;
import com.hulutas.catalog_service.specification.ProductSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final InventoryClientService inventoryClientService;

    @Override
    @Cacheable(value = "products", key = "'all_products'")
    public List<ProductResponse> getAll(Filter filter) {
        List<Product> products = productRepository.findAll(ProductSpecification.getProductSpecifications(filter));

        if(products.isEmpty())
            throw new ProductNotFoundException("No products found in the catalog with filtered");

        return products.stream().map(productMapper :: toDto).collect(Collectors.toList());
    }

    @Override
    public ProductResponse getByProductCode(String productCode) {
        Product product = productRepository.findByCode(productCode).orElseThrow(() -> new ProductNotFoundException("Product with code " + productCode + " not found"));


        return productMapper.toDto(product);
    }

    @Override
    @CacheEvict(value = "products", key = "'all_products'")
    @Transactional
    public ProductResponse createProduct(CreateOrUpdateRequest createOrUpdateRequest) {
        if (productRepository.findByCode(createOrUpdateRequest.code()).isPresent()) {
            throw new ProductAlreadyExistsException("Product with code " + createOrUpdateRequest.code() + " already exists.");
        }

        Product product = productMapper.toEntity(createOrUpdateRequest);
        Product response = productRepository.save(product);

        try {
            boolean stockSavedStatus = inventoryClientService.saveStockStatus(createOrUpdateRequest.code(), createOrUpdateRequest.stock());
            if (!stockSavedStatus)
                throw new RuntimeException("Failed to save stock via GRPC");
        } catch (Exception ex){
            throw new RuntimeException("Error while saving stock: " + ex.getMessage(), ex);
        }

        return productMapper.toDto(response);
    }

    @Override
    @CacheEvict(value = "products", key = "'all_products'")
    @Transactional
    public ProductResponse updateProduct(CreateOrUpdateRequest createOrUpdateRequest) {
        Product product = productRepository.findByCode(createOrUpdateRequest.code()).orElseThrow(() -> new ProductNotFoundException("Product with code " + createOrUpdateRequest.code() + " not found"));

        product.setPrice(createOrUpdateRequest.price());
        product.setHasStock(createOrUpdateRequest.hasStock());
        product.setName(createOrUpdateRequest.name());
        product.setUnlimited(createOrUpdateRequest.unlimited());
        product.setDescription(createOrUpdateRequest.description());

        Product response = productRepository.save(product);

        try {
            boolean updateStockStatus = inventoryClientService.updateStockStatus(createOrUpdateRequest.code(), createOrUpdateRequest.stock());
            if (!updateStockStatus)
                throw new RuntimeException("Failed to update stock via GRPC");
        } catch (Exception ex){
            throw new RuntimeException("Error while updating stock: " + ex.getMessage(), ex);
        }

        return productMapper.toDto(response);
    }

    @Override
    @CacheEvict(value = "products", key = "'all_products'")
    @Transactional
    public ProductResponse deleteProduct(String productCode) {
        Product product = productRepository.findByCode(productCode).orElseThrow(() -> new ProductNotFoundException("Product with code " + productCode + " not found"));

        product.setDeleted(true);

        Product response = productRepository.save(product);

        try {
            boolean stockDeletedStatus = inventoryClientService.deleteStockStatus(productCode, response.hashCode());
            if (!stockDeletedStatus)
                throw new RuntimeException("Failed to delete stock via GRPC");
        } catch (Exception ex){
            throw new RuntimeException("Error while deleting stock: " + ex.getMessage(), ex);
        }

        return productMapper.toDto(response);
    }
}
