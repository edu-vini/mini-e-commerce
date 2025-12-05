package com.grupo5.trainee.minsait.MiniEcommerce.service;

import com.grupo5.trainee.minsait.MiniEcommerce.dto.CategoryCreateDTO;
import com.grupo5.trainee.minsait.MiniEcommerce.dto.CategoryDTO;
import com.grupo5.trainee.minsait.MiniEcommerce.model.Category;
import com.grupo5.trainee.minsait.MiniEcommerce.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryDTO create(CategoryCreateDTO dto) {

        if (dto.name() == null || dto.name().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Nome da categoria é obrigatório.");
        }

        if (categoryRepository.existsByName(dto.name())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Categoria já existe.");
        }

        Category category = new Category();
        category.setName(dto.name());

        if (dto.parentId() != null) {
            Category parent = categoryRepository.findById(dto.parentId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Categoria pai não encontrada"));
            category.setParent(parent);
        }

        Category saved = categoryRepository.save(category);

        return CategoryDTO.builder()
                .id(saved.getId())
                .name(saved.getName())
                .parentId(saved.getParent() != null ? saved.getParent().getId() : null)
                .createdAt(saved.getCreatedAt())
                .updatedAt(saved.getUpdatedAt())
                .build();
    }

    public List<Category> listAll() {
        return categoryRepository.findAll();
    }

    public Category findById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Categoria não encontrada"));
    }

    public CategoryDTO update(Long id, CategoryCreateDTO dto) {

        Category category = findById(id);

        if (dto.name() != null && !dto.name().isBlank()) {
            category.setName(dto.name());
        }

        if (dto.parentId() != null) {
            Category parent = findById(dto.parentId());
            category.setParent(parent);
        }

        Category updated = categoryRepository.save(category);

        return CategoryDTO.builder()
                .id(updated.getId())
                .name(updated.getName())
                .parentId(updated.getParent() != null ? updated.getParent().getId() : null)
                .createdAt(updated.getCreatedAt())
                .updatedAt(updated.getUpdatedAt())
                .build();
    }

    public void delete(Long id) {
        Category category = findById(id);
        categoryRepository.delete(category);
    }
}
