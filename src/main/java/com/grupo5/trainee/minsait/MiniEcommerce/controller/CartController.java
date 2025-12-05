package com.grupo5.trainee.minsait.MiniEcommerce.controller;

import com.grupo5.trainee.minsait.MiniEcommerce.dto.AddItemRequest;
import com.grupo5.trainee.minsait.MiniEcommerce.dto.ResponseExceptionDTO;
import com.grupo5.trainee.minsait.MiniEcommerce.dto.UpdateItemRequest;
import com.grupo5.trainee.minsait.MiniEcommerce.model.Cart;
import com.grupo5.trainee.minsait.MiniEcommerce.model.User;
import com.grupo5.trainee.minsait.MiniEcommerce.service.CartService;
import com.grupo5.trainee.minsait.MiniEcommerce.service.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/cart")
@RequiredArgsConstructor
@Tag(name = "Carrinho", description = "Buscar e limpar carrinho. Adicionar, atualizar e remover itens")
public class CartController {

    private final CartService cartService;

    private Long getUserIdFromPrincipal(@AuthenticationPrincipal User userDetails) {
        return userDetails.getId();
    }

    @GetMapping("/active")
    @Operation(summary = "Buscar", description = "Busca ou cria um carrinho para o usuário.")
    @ApiResponse(responseCode = "200", description = "Carrinho retornado!", content = @Content(schema = @Schema(implementation = Cart.class)))
    public ResponseEntity<Cart> getCart(@AuthenticationPrincipal User userDetails) {
        Long userId = getUserIdFromPrincipal(userDetails);
        return ResponseEntity.ok(cartService.getOrCreateActiveCart(userId));
    }

    @PostMapping("/items")
    @Operation(summary = "Adicionar item", description = "Adiciona um produto à um carrinho existente ou cria um carrinho com este produto.")
    @ApiResponse(responseCode = "200", description = "Carrinho retornado!", content = @Content(schema = @Schema(implementation = Cart.class)))
    @ApiResponse(responseCode = "404", description = "Produto não encontrado!", content = @Content(schema = @Schema(implementation = ResponseExceptionDTO.class)))
    @ApiResponse(responseCode = "409", description = "Estoque insuficiente para o produto!", content = @Content(schema = @Schema(implementation = ResponseExceptionDTO.class)))
    public ResponseEntity<Cart> addItem(
            @AuthenticationPrincipal User userDetails,
            @RequestBody AddItemRequest request
    ) {
        Long userId = getUserIdFromPrincipal(userDetails);
        return ResponseEntity.ok(cartService.addItem(userId, request));
    }

    @PutMapping("/items/{itemId}")
    @Operation(summary = "Atualizar item", description = "Atualiza um produto de um carrinho de acordo com o id do item.")
    @ApiResponse(responseCode = "200", description = "Item atualizado!", content = @Content(schema = @Schema(implementation = Cart.class)))
    @ApiResponse(responseCode = "404", description = "Item não encontrado!", content = @Content(schema = @Schema(implementation = ResponseExceptionDTO.class)))
    @ApiResponse(responseCode = "409", description = "Estoque insuficiente para o produto!", content = @Content(schema = @Schema(implementation = ResponseExceptionDTO.class)))
    public ResponseEntity<Cart> updateItem(
            @AuthenticationPrincipal User userDetails,
            @PathVariable Long itemId,
            @RequestBody UpdateItemRequest request
    ) {
        Long userId = getUserIdFromPrincipal(userDetails);
        return ResponseEntity.ok(cartService.updateItem(userId, itemId, request));
    }

    @DeleteMapping("/items/{itemId}")
    @Operation(summary = "Remover item", description = "Remove um produto de um carrinho de acordo com o id do item.")
    @ApiResponse(responseCode = "200", description = "Item removido!", content = @Content(schema = @Schema(implementation = Cart.class)))
    @ApiResponse(responseCode = "404", description = "Item não encontrado!", content = @Content(schema = @Schema(implementation = ResponseExceptionDTO.class)))
    public ResponseEntity<Cart> removeItem(
            @AuthenticationPrincipal User userDetails,
            @PathVariable Long itemId
    ) {
        Long userId = getUserIdFromPrincipal(userDetails);
        return ResponseEntity.ok(cartService.removeItem(userId, itemId));
    }

    @DeleteMapping("/clear")
    @Operation(summary = "Limpar carrinho", description = "Exclui um carrinho de acordo com o id.")
    @ApiResponse(responseCode = "204", description = "Carrinho excluído!")
    public ResponseEntity<Void> clearCart(@AuthenticationPrincipal User userDetails) {
        Long userId = getUserIdFromPrincipal(userDetails);
        cartService.clearCart(userId);
        return ResponseEntity.noContent().build();
    }
}