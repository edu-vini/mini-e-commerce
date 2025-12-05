package com.grupo5.trainee.minsait.MiniEcommerce.repository;

import com.grupo5.trainee.minsait.MiniEcommerce.model.InventoryTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InventoryTransactionRepository extends JpaRepository<InventoryTransaction, Long> {
    List<InventoryTransaction> findByProductId(Long id);
}
