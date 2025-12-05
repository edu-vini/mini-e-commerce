package com.grupo5.trainee.minsait.MiniEcommerce.service;

import com.grupo5.trainee.minsait.MiniEcommerce.dto.OrderCreateDTO;
import com.grupo5.trainee.minsait.MiniEcommerce.dto.OrderDTO;
import com.grupo5.trainee.minsait.MiniEcommerce.enums.CartStatus;
import com.grupo5.trainee.minsait.MiniEcommerce.enums.InventoryTransactionReason;
import com.grupo5.trainee.minsait.MiniEcommerce.enums.OrderStatus;
import com.grupo5.trainee.minsait.MiniEcommerce.model.*;
import com.grupo5.trainee.minsait.MiniEcommerce.repository.CartRepository;
import com.grupo5.trainee.minsait.MiniEcommerce.repository.InventoryTransactionRepository;
import com.grupo5.trainee.minsait.MiniEcommerce.repository.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final InventoryService inventoryService;
    private final InventoryTransactionRepository inventoryRepository;

    public OrderDTO create(OrderCreateDTO dto){
        Cart cart = this.cartRepository
                .findById(dto.cartId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Carrinho não encontrado!"));

        if(cart.getStatus() != CartStatus.OPEN){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Não é possível criar pedido a partir de carrinho cancelado ou finalizado!");
        }

        List<CartItem> cartItems = cart.getItems();
        ArrayList<OrderItem> orderItems = new ArrayList<OrderItem>();

        BigDecimal subtotal = BigDecimal.ZERO;

        Order order = new Order();

        for(CartItem item: cartItems){
            Product product = item.getProduct();
            Integer orderedQuantity = item.getQuantity();

            if(product.availableStock(orderedQuantity)){
                OrderItem orderItem = new OrderItem();
                orderItem.setProduct(product);
                orderItem.setQuantity(orderedQuantity);
                orderItem.setPriceSnapshot(item.getPriceSnapshot());

                orderItem.setOrder(order);

                orderItems.add(orderItem);

                subtotal = subtotal.add(item.getPriceSnapshot().multiply(BigDecimal.valueOf(item.getQuantity())));
            }
        }

        BigDecimal total = subtotal.add(dto.freight()).subtract(dto.discount());

        order.setStatus(OrderStatus.CREATED);
        order.setUserId(cart.getUserId());
        order.setAddress(dto.address());
        order.setSubtotal(subtotal);
        order.setDiscount(dto.discount());
        order.setFreight(dto.freight());
        order.setTotal(total);
        order.setItems(orderItems);

        this.orderRepository.save(order);

        for(OrderItem item: order.getItems()){
            InventoryTransaction transaction = this.inventoryService.manageStock(item.getProduct().getId(), item.getQuantity(), InventoryTransactionReason.SALE);
            transaction.setReferenceId(order.getId());
            this.inventoryRepository.save(transaction);
        }

        cart.setStatus(CartStatus.FINISHED);
        this.cartRepository.save(cart);

        return OrderDTO.fromEntity(order);
    }

    public OrderDTO getById(Long id){
        return OrderDTO.fromEntity(
                this.orderRepository
                        .findById(id)
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pedido não encontrado!"))
        );
    }

    public List<OrderDTO> getAll(){
        return orderRepository.findAll()
                .stream()
                .map(OrderDTO::fromEntity)
                .toList();
    }

    public OrderDTO cancel(Long id){
        Order order = this.orderRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pedido não encontrado!"));

        if(order.getStatus().isAfter(OrderStatus.PAID)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Não é possivel cancelar pedido após o envio!");
        }

        order.setStatus(OrderStatus.CANCELLED);

        for(OrderItem item: order.getItems()){
            InventoryTransaction transaction = this.inventoryService.manageStock(item.getProduct().getId(), item.getQuantity(), InventoryTransactionReason.ORDER_CANCELLED);
            transaction.setReferenceId(order.getId());
            this.inventoryRepository.save(transaction);
        }

        this.orderRepository.save(order);

        return OrderDTO.fromEntity(order);
    }

    public OrderDTO manageStatus(Long id, OrderStatus status){
        Order order = this.orderRepository.findById(id)
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND, "Pedido não encontrado!"));

        if(order.getStatus().isAfter(status)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Não é possivel retroceder status do pedido!");
        }

        order.setStatus(status);
        this.orderRepository.save(order);
        return OrderDTO.fromEntity(order);
    }
}