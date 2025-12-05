package com.grupo5.trainee.minsait.MiniEcommerce.model;

import com.grupo5.trainee.minsait.MiniEcommerce.enums.OrderStatus;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private BigDecimal subtotal;
    private BigDecimal discount;
    private BigDecimal freight;
    private BigDecimal total;
    @Enumerated(EnumType.STRING)
    private OrderStatus status;
    @Embedded
    @Valid
    private Address address;
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> items = new ArrayList<>();
    @CreatedDate
    @Column(nullable = false, updatable = false)
    private Date createdAt;
}
