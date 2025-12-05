package com.grupo5.trainee.minsait.MiniEcommerce.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
public class ProductDTO {
        Long id;
        String name;
        String description;
        String sku;
        BigDecimal price;
        BigDecimal costPrice;
        String categoryName;
        Integer stockQuantity;
        Integer minimumStockQuantity;
        boolean active;
        Date createdAt;
        Date updatedAt;

    public ProductDTO(Long id, String name, String description, String sku, BigDecimal price, BigDecimal costPrice, String categoryName, Integer stockQuantity, boolean active, Date createdAt, Date updatedAt) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.sku = sku;
        this.price = price;
        this.costPrice = costPrice;
        this.categoryName = categoryName;
        this.stockQuantity = stockQuantity;
        this.active = active;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;

    }
    public boolean low_stock(){
        return this.stockQuantity <= this.minimumStockQuantity;
    }
}
