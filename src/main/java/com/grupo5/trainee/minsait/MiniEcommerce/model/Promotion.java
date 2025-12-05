package com.grupo5.trainee.minsait.MiniEcommerce.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "promotions")
public class Promotion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String code;
    private String type;

    @Column(name = "promotion_value")
    private BigDecimal value;

    private Date validFrom;
    private Date validTo;
    private Integer usageLimit;
    private Integer usedCount;
    private String applicableToo;
}
