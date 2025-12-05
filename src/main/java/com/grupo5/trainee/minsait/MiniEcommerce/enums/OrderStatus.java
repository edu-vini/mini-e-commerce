package com.grupo5.trainee.minsait.MiniEcommerce.enums;

public enum OrderStatus {
    CREATED,
    PAID,
    SHIPPED,
    DELIVERED,
    CANCELLED;

    public boolean isAfter(OrderStatus other) {
        return this.ordinal() > other.ordinal();
    }
}
