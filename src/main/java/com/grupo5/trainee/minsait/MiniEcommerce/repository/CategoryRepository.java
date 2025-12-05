package com.grupo5.trainee.minsait.MiniEcommerce.repository;

import com.grupo5.trainee.minsait.MiniEcommerce.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    boolean existsByName(String name);
    Optional<Category> findByName(String name);
}
