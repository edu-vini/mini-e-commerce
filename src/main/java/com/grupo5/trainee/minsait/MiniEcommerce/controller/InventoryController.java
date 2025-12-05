package com.grupo5.trainee.minsait.MiniEcommerce.controller;

import com.grupo5.trainee.minsait.MiniEcommerce.dto.TransactionManageDTO;
import com.grupo5.trainee.minsait.MiniEcommerce.enums.InventoryTransactionReason;
import com.grupo5.trainee.minsait.MiniEcommerce.model.InventoryTransaction;
import com.grupo5.trainee.minsait.MiniEcommerce.service.InventoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/inventory")
@Tag(name = "Inventário", description = "Adicionar ou remover produtos do estoque. Listar e buscar transações de estoque.")
public class InventoryController {
    private final InventoryService inventoryService;

    @PostMapping("/{productId}/add")
    @Operation(summary = "Adicionar no estoque", description = "Aumenta a quantidade de estoque de um produto")
    @ApiResponse(responseCode = "200", description = "Produto adicionado no estoque!")
    @ApiResponse(responseCode = "404", description = "Produto não encontrado!")
    public ResponseEntity<TransactionManageDTO> addStock(@PathVariable Long productId, @RequestParam Integer quantity) {
        InventoryTransaction transaction = this.inventoryService.manageStock(productId, Math.abs(quantity), InventoryTransactionReason.MANUAL_ADJUST);
        return ResponseEntity.ok(TransactionManageDTO.fromEntity(transaction));
    }

    @PostMapping("/{productId}/remove")
    @Operation(summary = "Remover do estoque", description = "Diminui a quantidade de estoque de um produto")
    @ApiResponse(responseCode = "200", description = "Produto removido do estoque!")
    @ApiResponse(responseCode = "409", description = "Estoque insuficiente para venda!")
    public ResponseEntity<TransactionManageDTO> removeStock(@PathVariable Long productId, @RequestParam int quantity) {
        InventoryTransaction transaction = this.inventoryService.manageStock(productId, Math.abs(quantity) * -1, InventoryTransactionReason.MANUAL_ADJUST);
        return ResponseEntity.ok(TransactionManageDTO.fromEntity(transaction));
    }

    @GetMapping
    @Operation(summary = "Listar todas", description = "Retorna todas as transações de estoque.")
    public ResponseEntity<List<InventoryTransaction>> getTransaction() {
        return ResponseEntity.ok(inventoryService.getAll());
    }

    @GetMapping("/{productId}")
    @Operation(summary = "Buscar", description = "Retorna as transações de um produto pelo id.")
    @ApiResponse(responseCode = "200", description = "Produto encontrado no estoque!")
    @ApiResponse(responseCode = "404", description = "Produto não encontrado no estoque.")
    public ResponseEntity<List<InventoryTransaction>> getTransactionForProductId(@PathVariable Long productId) {
        return ResponseEntity.ok(inventoryService.getByProductId(productId));
    }
}


