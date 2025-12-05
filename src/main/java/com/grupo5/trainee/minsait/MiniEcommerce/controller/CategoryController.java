package com.grupo5.trainee.minsait.MiniEcommerce.controller;

import com.grupo5.trainee.minsait.MiniEcommerce.dto.CategoryCreateDTO;
import com.grupo5.trainee.minsait.MiniEcommerce.dto.CategoryDTO;
import com.grupo5.trainee.minsait.MiniEcommerce.dto.ProductDTO;
import com.grupo5.trainee.minsait.MiniEcommerce.dto.ResponseExceptionDTO;
import com.grupo5.trainee.minsait.MiniEcommerce.model.Category;
import com.grupo5.trainee.minsait.MiniEcommerce.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/categories")
@Tag(name = "Categorias", description = "Criar, buscar, atualizar e excluir categorias")
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    @Operation(summary = "Criar", description = "Adiciona uma categoria.")
    @ApiResponse(responseCode = "200", description = "Categoria adicionada!", content = @Content(schema = @Schema(implementation = CategoryDTO.class)))
    @ApiResponse(responseCode = "400", description = "Nome da categoria é obrigatório.", content = @Content(schema = @Schema(implementation = ResponseExceptionDTO.class)))
    @ApiResponse(responseCode = "409", description = "Categoria já existe.", content = @Content(schema = @Schema(implementation = ResponseExceptionDTO.class)))
    @ApiResponse(responseCode = "404", description = "Categoria pai não encontrada", content = @Content(schema = @Schema(implementation = ResponseExceptionDTO.class)))
    public ResponseEntity<CategoryDTO> create(@RequestBody CategoryCreateDTO dto) {
        return ResponseEntity.ok(categoryService.create(dto));
    }

    @GetMapping
    @Operation(summary = "Listar todas", description = "Retorna todas as categorias.")
    public ResponseEntity<List<Category>> listAll() {
        return ResponseEntity.ok(categoryService.listAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar", description = "Retorna uma categoria de acordo com o id.")
    @ApiResponse(responseCode = "404", description = "Categoria não encontrada", content = @Content(schema = @Schema(implementation = ResponseExceptionDTO.class)))
    @ApiResponse(responseCode = "200", description = "Categoria encontrada!", content = @Content(schema = @Schema(implementation = CategoryDTO.class)))
    public ResponseEntity<Category> findById(@PathVariable Long id) {
        return ResponseEntity.ok(categoryService.findById(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar", description = "Atualiza uma categoria de acordo com o id;")
    @ApiResponse(responseCode = "404", description = "Categoria não encontrada", content = @Content(schema = @Schema(implementation = ResponseExceptionDTO.class)))
    @ApiResponse(responseCode = "200", description = "Categoria atualizada!", content = @Content(schema = @Schema(implementation = CategoryDTO.class)))
    public ResponseEntity<CategoryDTO> update(@PathVariable Long id, @RequestBody CategoryCreateDTO dto) {
        return ResponseEntity.ok(categoryService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir", description = "Exclui uma categoria de acordo com o id.")
    @ApiResponse(responseCode = "404", description = "Categoria não encontrada", content = @Content(schema = @Schema(implementation = ResponseExceptionDTO.class)))
    @ApiResponse(responseCode = "200", description = "Categoria excluída!")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        categoryService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
