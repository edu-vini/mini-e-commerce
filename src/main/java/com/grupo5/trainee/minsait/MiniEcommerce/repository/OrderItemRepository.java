package com.grupo5.trainee.minsait.MiniEcommerce.repository;

import com.grupo5.trainee.minsait.MiniEcommerce.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
