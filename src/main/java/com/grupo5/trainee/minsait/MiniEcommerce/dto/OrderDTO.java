package com.grupo5.trainee.minsait.MiniEcommerce.dto;

import com.grupo5.trainee.minsait.MiniEcommerce.enums.OrderStatus;
import com.grupo5.trainee.minsait.MiniEcommerce.model.Order;
import lombok.Builder;

import java.math.BigDecimal;
import java.util.List;

@Builder
public record OrderDTO(
        Long id,
        Long userId,
        BigDecimal subtotal,
        BigDecimal discount,
        BigDecimal freight,
        BigDecimal total,
        OrderStatus status,
        List<OrderItemDTO> items
) {
    public static OrderDTO fromEntity(Order order) {
        return new OrderDTO(
                order.getId(),
                order.getUserId(),
                order.getSubtotal(),
                order.getDiscount(),
                order.getFreight(),
                order.getTotal(),
                order.getStatus(),
                order.getItems()
                        .stream()
                        .map(OrderItemDTO::fromEntity)
                        .toList()
        );
    }
}
