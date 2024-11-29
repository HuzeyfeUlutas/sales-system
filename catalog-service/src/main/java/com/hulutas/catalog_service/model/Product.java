package com.hulutas.catalog_service.model;

import com.hulutas.catalog_service.model.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.UUID;

@Entity
@Table(name = "products")
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class Product extends BaseEntity<UUID> {
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "product_code", nullable = false, unique = true)
    private String code;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "price", nullable = false)
    private double price;

    @Column(name = "has_stock", nullable = false)
    private boolean hasStock;
}
