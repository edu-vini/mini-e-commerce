package com.grupo5.trainee.minsait.MiniEcommerce.controller;

import com.grupo5.trainee.minsait.MiniEcommerce.dto.*;
import com.grupo5.trainee.minsait.MiniEcommerce.service.ProductService;
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
@RequestMapping("/v1/products")
@Tag(name = "Produtos", description = "Criar, buscar, atualizar e excluir produtos")

public class ProductController {

    private final ProductService productService;

    @PostMapping
    @Operation(summary = "Criar", description = "Adiciona um produto.")
    @ApiResponse(responseCode = "200", description = "Produto adicionado!", content = @Content(schema = @Schema(implementation = ProductDTO.class)))
    @ApiResponse(responseCode = "404", description = "Categoria não encontrada", content = @Content(schema = @Schema(implementation = ResponseExceptionDTO.class)))
    public ResponseEntity<ProductDTO> create(@RequestBody ProductCreateDTO dto) {
        return ResponseEntity.ok(productService.create(dto));
    }

    @GetMapping
    @Operation(summary = "Listar todos", description = "Retorna todos os produtos.")
    public ResponseEntity<List<ProductDTO>> getAll() {
        return ResponseEntity.ok(productService.findAll());
    }

    @GetMapping("{id}")
    @Operation(summary = "Buscar", description = "Retorna um produto de acordo com o id.")
    @ApiResponse(responseCode = "404", description = "Produto não encontrado", content = @Content(schema = @Schema(implementation = ResponseExceptionDTO.class)))
    @ApiResponse(responseCode = "200", description = "Produto econtrado!", content = @Content(schema = @Schema(implementation = ProductDTO.class)))
    public ResponseEntity<ProductDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.findById(id));
    }

    @PutMapping
    @Operation(summary = "Atualizar", description = "Atualiza um produto. id do produto e da categoria são obrigatórios para esse método.")
    @ApiResponse(responseCode = "404", description = "Produto não encontrado", content = @Content(schema = @Schema(implementation = ResponseExceptionDTO.class)))
    @ApiResponse(responseCode = "404", description = "Categoria não encontrada", content = @Content(schema = @Schema(implementation = ResponseExceptionDTO.class)))
    @ApiResponse(responseCode = "200", description = "Produto atualizado!", content = @Content(schema = @Schema(implementation = ProductDTO.class)))
    public ResponseEntity<ProductDTO> update(@RequestBody ProductUpdateDTO dto) {
        return ResponseEntity.ok(productService.update(dto));
    }

    @DeleteMapping("{id}")
    @Operation(summary = "Excluir", description = "Exclui um produto de acordo com o id.")
    @ApiResponse(responseCode = "404", description = "Produto não encontrado", content = @Content(schema = @Schema(implementation = ResponseExceptionDTO.class)))
    @ApiResponse(responseCode = "204", description = "Produto excluído!")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
