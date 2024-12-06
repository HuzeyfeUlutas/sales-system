package com.hulutas.catalog_service.controller;


import com.hulutas.catalog_service.dto.requests.CreateOrUpdateRequest;
import com.hulutas.catalog_service.dto.requests.Filter;
import com.hulutas.catalog_service.dto.responses.ProductResponse;
import com.hulutas.catalog_service.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("catalog/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/check/{productCode}")
    public boolean checkStockWithProductId(@PathVariable String productCode) {

        return productService.checkStockByProductId(productCode);
    }

    @GetMapping()
    public List<ProductResponse> getAll(@ModelAttribute Filter filter) {

        return productService.getAll(filter);
    }

    @GetMapping("/{productCode}")
    public ProductResponse getByCode(@PathVariable String productCode){

        return productService.getByProductCode(productCode);
    }

    @PostMapping()
    public ProductResponse add(@Valid @RequestBody CreateOrUpdateRequest createOrUpdateRequest) {

        return productService.createProduct(createOrUpdateRequest);
    }

    @PutMapping()
    public ProductResponse update(@RequestBody CreateOrUpdateRequest createOrUpdateRequest) {

        return productService.updateProduct(createOrUpdateRequest);
    }

    @DeleteMapping("/{productCode}")
    public ProductResponse delete(@PathVariable String productCode) {

        return productService.deleteProduct(productCode);
    }

}
