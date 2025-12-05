package com.grupo5.trainee.minsait.MiniEcommerce.repository;

import com.grupo5.trainee.minsait.MiniEcommerce.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    @Query(value = "SELECT * FROM cart_items WHERE cart_id = :cartId AND product_id = :productId", nativeQuery = true)
    Optional<CartItem> findByCartIdAndProductId(Long cartId, Long productId);
}
