package com.grupo5.trainee.minsait.MiniEcommerce.dto;

import java.math.BigDecimal;

public record ProductUpdateDTO(
        Long id,
        String name,
        String description,
        String sku,
        BigDecimal price,
        BigDecimal costPrice,
        Long categoryId,
        Integer stockQuantity,
        boolean active
) {}
