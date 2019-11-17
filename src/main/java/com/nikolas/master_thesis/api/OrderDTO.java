package com.nikolas.master_thesis.api;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Set;

public class OrderDTO {
    private Long id;
    private double total;
    private String orderDate;
    private Set<OrderItemDTO> orderItems;
    private Long userId;

    public OrderDTO() {
    }

    public OrderDTO(double total, String orderDate, Long userId) {
        this.total = total;
        this.orderDate = orderDate;
        this.userId = userId;
    }

    public OrderDTO(Long id, double total, String orderDate, Long userId) {
        this.id = id;
        this.total = total;
        this.orderDate = orderDate;
        this.userId = userId;
    }

    public Long getId() {
        return id;
    }

    public double getTotal() {
        return total;
    }

    @JsonProperty("order_date")
    public String getOrderDate() {
        return orderDate;
    }

    public Set<OrderItemDTO> getOrderItems() {
        return orderItems;
    }

    @JsonProperty("user_id")
    public Long getUserId() {
        return userId;
    }
}
