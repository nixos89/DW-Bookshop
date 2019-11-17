package com.nikolas.master_thesis.api;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OrderItemDTO {
    private Long id;
    private int amount;
    private Long bookId;
    private Long orderId;

    public OrderItemDTO() {
    }

    public OrderItemDTO(int amount, Long bookId, Long orderId) {
        this.amount = amount;
        this.bookId = bookId;
        this.orderId = orderId;
    }

    public OrderItemDTO(Long id, int amount, Long bookId, Long orderId) {
        this.id = id;
        this.amount = amount;
        this.bookId = bookId;
        this.orderId = orderId;
    }

    @JsonProperty("id")
    public Long getId() {
        return id;
    }

    @JsonProperty("amount")
    public int getAmount() {
        return amount;
    }

    @JsonProperty("book_id")
    public Long getBookId() {
        return bookId;
    }

    @JsonProperty("order_id")
    public Long getOrderId() {
        return orderId;
    }
}
