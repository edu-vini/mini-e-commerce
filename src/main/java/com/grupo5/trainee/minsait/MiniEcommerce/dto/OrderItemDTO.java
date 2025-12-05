package com.grupo5.trainee.minsait.MiniEcommerce.dto;

import com.grupo5.trainee.minsait.MiniEcommerce.model.OrderItem;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record OrderItemDTO(
        Long productId,
        String productName,
        Integer quantity,
        BigDecimal price
) {
    public static OrderItemDTO fromEntity(OrderItem item) {
        return new OrderItemDTO(
                item.getProduct().getId(),
                item.getProduct().getName(),
                item.getQuantity(),
                item.getPriceSnapshot()
        );
    }
}
