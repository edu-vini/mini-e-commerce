package com.grupo5.trainee.minsait.MiniEcommerce.dto;

import com.grupo5.trainee.minsait.MiniEcommerce.model.Address;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record OrderCreateDTO(
        @NotNull
        Long cartId,

        BigDecimal discount,
        BigDecimal freight,

        @NotNull
        @Valid
        Address address
) {
}
