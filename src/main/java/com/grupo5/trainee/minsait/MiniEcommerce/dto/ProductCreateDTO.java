package com.grupo5.trainee.minsait.MiniEcommerce.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

public record ProductCreateDTO(
        String name,
        String description,
        String sku,
        BigDecimal price,
        BigDecimal costPrice,
        @Schema(defaultValue = "1")
        Long categoryId,
        @Schema(defaultValue = "1")
        Integer stockQuantity,
        boolean active
) {}
