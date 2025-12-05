package com.grupo5.trainee.minsait.MiniEcommerce.service;

import com.grupo5.trainee.minsait.MiniEcommerce.dto.ProductCreateDTO;
import com.grupo5.trainee.minsait.MiniEcommerce.dto.ProductDTO;
import com.grupo5.trainee.minsait.MiniEcommerce.dto.ProductUpdateDTO;
import com.grupo5.trainee.minsait.MiniEcommerce.model.Category;
import com.grupo5.trainee.minsait.MiniEcommerce.model.Product;
import com.grupo5.trainee.minsait.MiniEcommerce.repository.CategoryRepository;
import com.grupo5.trainee.minsait.MiniEcommerce.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductDTO create(ProductCreateDTO dto) {

        Category category = categoryRepository.findById(dto.categoryId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Categoria não encontrada"));

        Product p = new Product();
        p.setName(dto.name());
        p.setDescription(dto.description());
        p.setSku(dto.sku());
        p.setPrice(dto.price());
        p.setCostPrice(dto.costPrice());
        p.setCategory(category);
        p.setStockQuantity(dto.stockQuantity());
        p.setActive(dto.active());
        p.setCreatedAt(new Date());

        Product saved = productRepository.save(p);

        return mapToDTO(saved);
    }

    public List<ProductDTO> findAll() {
        return productRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    public ProductDTO findById(Long id) {
        Product p = productRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado"));

        return mapToDTO(p);
    }

    public ProductDTO update(ProductUpdateDTO dto) {

        Product p = productRepository.findById(dto.id())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado"));

        Category category = categoryRepository.findById(dto.categoryId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Categoria não encontrada"));

        p.setName(dto.name());
        p.setDescription(dto.description());
        p.setSku(dto.sku());
        p.setPrice(dto.price());
        p.setCostPrice(dto.costPrice());
        p.setCategory(category);
        p.setStockQuantity(dto.stockQuantity());
        p.setActive(dto.active());
        p.setUpdatedAt(new Date());

        Product saved = productRepository.save(p);

        return mapToDTO(saved);
    }

    public void delete(Long id) {
        Product p = productRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        productRepository.delete(p);
    }

    private ProductDTO mapToDTO(Product p) {
        return new ProductDTO(
                p.getId(),
                p.getName(),
                p.getDescription(),
                p.getSku(),
                p.getPrice(),
                p.getCostPrice(),
                p.getCategory() != null ? p.getCategory().getName() : null,
                p.getStockQuantity(),
                p.isActive(),
                p.getCreatedAt(),
                p.getUpdatedAt()
        );
    }
}
