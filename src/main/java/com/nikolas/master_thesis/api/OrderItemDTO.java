package com.nikolas.master_thesis.api;

import com.fasterxml.jackson.annotation.JsonProperty;

// FIXME: impelemnt Lombok annotations to behave as Immutable POJO
public class OrderItemDTO {
    private Long orderItemId;
    private int amount;
    private BookDTO2 book;
    private Long orderId;
    private double totalOrderItemPrice;

    public OrderItemDTO() {
    }

    public OrderItemDTO(Long orderItemId, int amount, BookDTO2 book, Long orderId, double totalOrderItemPrice) {
        this.orderItemId = orderItemId;
        this.amount = amount;
        this.book = book;
        this.orderId = orderId;
        this.totalOrderItemPrice = totalOrderItemPrice;
    }

    @JsonProperty("order_item_id")
    public Long getOrderItemId() {
        return orderItemId;
    }

    @JsonProperty("amount")
    public int getAmount() {
        return amount;
    }

    @JsonProperty("book")
    public BookDTO2 getBook() {
        return book;
    }

    @JsonProperty("order_id")
    public Long getOrderId() {
        return orderId;
    }


    public double getTotalOrderItemPrice() {
        return totalOrderItemPrice;
    }
}
