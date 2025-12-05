package com.grupo5.trainee.minsait.MiniEcommerce.dto;

import lombok.Builder;

import java.util.Date;

@Builder
public record CategoryDTO(
        Long id,
        String name,
        Long parentId,
        Date createdAt,
        Date updatedAt
) {}
