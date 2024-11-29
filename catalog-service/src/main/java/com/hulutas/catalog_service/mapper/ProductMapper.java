package com.hulutas.catalog_service.mapper;

import com.hulutas.catalog_service.dto.requests.CreateOrUpdateRequest;
import com.hulutas.catalog_service.dto.responses.ProductResponse;
import com.hulutas.catalog_service.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductResponse toDto(Product product);

    Product toEntity(CreateOrUpdateRequest createOrUpdateRequest);
}
