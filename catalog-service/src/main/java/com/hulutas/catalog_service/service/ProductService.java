package com.hulutas.catalog_service.service;

import com.hulutas.catalog_service.dto.requests.CreateOrUpdateRequest;
import com.hulutas.catalog_service.dto.requests.Filter;
import com.hulutas.catalog_service.dto.responses.ProductResponse;

import java.util.List;

public interface ProductService {
    List<ProductResponse> getAll(Filter filter);
    ProductResponse getByProductCode(String productCode);
    ProductResponse createProduct(CreateOrUpdateRequest createOrUpdateRequest);
    ProductResponse updateProduct(CreateOrUpdateRequest createOrUpdateRequest);
    ProductResponse deleteProduct(String productCode);

}
