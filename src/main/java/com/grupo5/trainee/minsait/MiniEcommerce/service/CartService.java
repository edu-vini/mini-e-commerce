package com.grupo5.trainee.minsait.MiniEcommerce.service;

import com.grupo5.trainee.minsait.MiniEcommerce.dto.AddItemRequest;
import com.grupo5.trainee.minsait.MiniEcommerce.dto.UpdateItemRequest;
import com.grupo5.trainee.minsait.MiniEcommerce.enums.CartStatus;
import com.grupo5.trainee.minsait.MiniEcommerce.model.Cart;
import com.grupo5.trainee.minsait.MiniEcommerce.model.CartItem;
import com.grupo5.trainee.minsait.MiniEcommerce.model.Product;
import com.grupo5.trainee.minsait.MiniEcommerce.repository.CartItemRepository;
import com.grupo5.trainee.minsait.MiniEcommerce.repository.CartRepository;
import com.grupo5.trainee.minsait.MiniEcommerce.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartService {

    private final ProductRepository productRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;

    public Cart getOrCreateActiveCart(Long userId) {
        return cartRepository.findByUserId(userId)
                .orElseGet(() -> {
                    Cart cart = new Cart();
                    cart.setUserId(userId);
                    cart.setStatus(CartStatus.OPEN);
                    return cartRepository.save(cart);
                });
    }

    public Cart addItem(Long userId, AddItemRequest req) {
        Cart cart = getOrCreateActiveCart(userId);

        Optional<CartItem> existingItem = this.cartItemRepository
                .findByCartIdAndProductId(
                        cart.getId(),
                        req.productId()
                );

        Product product = this.productRepository
                .findById(req.productId())
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado!"));

        if(product.availableStock(req.quantity())) {
            if (existingItem.isPresent()) {
                CartItem item = existingItem.get();
                item.setQuantity(item.getQuantity() + req.quantity());
                cartItemRepository.save(item);
            } else {
                CartItem newItem = new CartItem();
                newItem.setProduct(product);
                newItem.setQuantity(req.quantity());
                newItem.setPriceSnapshot(product.getPrice());
                newItem.setCart(cart);
                cartItemRepository.save(newItem);
                if(cart.getItems() == null){
                    cart.setItems(new java.util.ArrayList<>());
                } else {
                    cart.getItems().add(newItem);
                }
            }
            return cartRepository.save(cart);
        }

        return cart;
    }

    public Cart updateItem(Long userId, Long itemId, UpdateItemRequest req) {
        Cart cart = getOrCreateActiveCart(userId);

        CartItem item = cartItemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Item não encontrado"));

        if (req.quantity() <= 0) {
            return removeItem(userId, itemId);
        }
        if(item.getProduct().availableStock(req.quantity())) {
            item.setQuantity(req.quantity());
            cartItemRepository.save(item);

            return cartRepository.save(cart);
        }
        return cart;
    }

    public Cart removeItem(Long userId, Long itemId) {
        Cart cart = getOrCreateActiveCart(userId);

        CartItem item = cartItemRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Item não encontrado"));

        cartItemRepository.delete(item);
        cart.getItems().remove(item);

        return cartRepository.save(cart);
    }

    public void clearCart(Long userId) {
        Cart cart = getOrCreateActiveCart(userId);
        cartRepository.delete(cart);
    }

}
