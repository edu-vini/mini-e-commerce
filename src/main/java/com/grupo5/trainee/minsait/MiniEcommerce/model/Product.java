package com.grupo5.trainee.minsait.MiniEcommerce.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private String sku;
    private BigDecimal price;
    private BigDecimal costPrice;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
    @ColumnDefault("0")
    private Integer stockQuantity;
    @ColumnDefault("true")
    private boolean active;
    @CreatedDate
    private Date createdAt;
    @LastModifiedDate
    private Date updatedAt;

    public boolean availableStock( Integer quantityRequired) {
        if(this.stockQuantity < quantityRequired){
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    String.format("Estoque insuficiente para o produto '%s'. Solicitado: %d. Disponível: %d.", this.name, quantityRequired, this.stockQuantity)
            );
        }
        return true;
    }

}
