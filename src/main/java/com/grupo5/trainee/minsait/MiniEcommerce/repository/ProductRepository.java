package com.grupo5.trainee.minsait.MiniEcommerce.repository;

import com.grupo5.trainee.minsait.MiniEcommerce.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
