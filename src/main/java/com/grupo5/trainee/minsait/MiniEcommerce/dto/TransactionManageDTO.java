package com.grupo5.trainee.minsait.MiniEcommerce.dto;

import com.grupo5.trainee.minsait.MiniEcommerce.enums.InventoryTransactionReason;
import com.grupo5.trainee.minsait.MiniEcommerce.model.InventoryTransaction;
import lombok.Builder;


@Builder
public record TransactionManageDTO(
        String productName,
        Integer delta,
        InventoryTransactionReason reason
) {
    public static TransactionManageDTO fromEntity(InventoryTransaction transaction){
        return TransactionManageDTO
                .builder()
                .productName(transaction.getProduct().getName())
                .delta(transaction.getDelta())
                .reason(transaction.getReason())
                .build();
    }

}
