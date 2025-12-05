package com.grupo5.trainee.minsait.MiniEcommerce.service;

import com.grupo5.trainee.minsait.MiniEcommerce.enums.InventoryTransactionReason;
import com.grupo5.trainee.minsait.MiniEcommerce.model.Product;
import com.grupo5.trainee.minsait.MiniEcommerce.repository.InventoryTransactionRepository;
import com.grupo5.trainee.minsait.MiniEcommerce.repository.ProductRepository;
import com.grupo5.trainee.minsait.MiniEcommerce.model.InventoryTransaction;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@AllArgsConstructor
public class InventoryService {

    private final ProductRepository productRepository;
    private final InventoryTransactionRepository inventoryTransactionRepository;

    public InventoryTransaction manageStock(Long productId, Integer quantity, InventoryTransactionReason reason) {
        Product product = this.productRepository.findById(productId)
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado!"));

        if(quantity < 0 || reason == InventoryTransactionReason.SALE){
            product.availableStock(Math.abs(quantity));
        }

        InventoryTransaction transaction = InventoryTransaction
                .builder()
                .delta(quantity)
                .reason(reason)
                .build();

        if(reason == InventoryTransactionReason.SALE){
            transaction.setDelta( Math.abs(quantity) * -1 );
        } else if (reason == InventoryTransactionReason.ORDER_CANCELLED){
            transaction.setDelta( Math.abs(quantity) );
        }

        product.setStockQuantity(product.getStockQuantity() + transaction.getDelta());
        transaction.setProduct(product);

        return this.inventoryTransactionRepository.save(transaction);
    }

    public List<InventoryTransaction> getByProductId(Long productId){
        return this.inventoryTransactionRepository.findByProductId(productId);
    }

    public List<InventoryTransaction> getAll(){
        return this.inventoryTransactionRepository.findAll();
    }
}
