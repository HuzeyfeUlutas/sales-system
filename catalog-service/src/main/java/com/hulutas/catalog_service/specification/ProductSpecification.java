package com.hulutas.catalog_service.specification;

import com.hulutas.catalog_service.dto.requests.Filter;
import com.hulutas.catalog_service.model.Product;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class ProductSpecification {
    public static Specification<Product> getProductSpecifications(Filter filter) {
        return (Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filter.isDeleted()) {
                predicates.add(criteriaBuilder.equal(root.get("isDeleted"), true));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
