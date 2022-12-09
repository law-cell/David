package com.murex.retail.model.order;

public enum OrderStatus {

    RECEIVED("Order Received"),
    IN_PROGRESS("Order In-Progress"),
    READY("Order Ready");

    private final String value;

    OrderStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}