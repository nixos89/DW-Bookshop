package com.nikolas.master_thesis.api;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class OrderDTO {
    private Long orderId;
    private double totalPrice;
    private String orderDate;
    private List<OrderItemDTO> orderItems;
    private UserDTO userDTO;

    public OrderDTO() {
    }

    public OrderDTO(Long orderId, double totalPrice, String orderDate, List<OrderItemDTO> orderItems, UserDTO userDTO) {
        this.orderId = orderId;
        this.totalPrice = totalPrice;
        this.orderDate = orderDate;
        this.orderItems = orderItems;
        this.userDTO = userDTO;
    }

    @JsonProperty("order_id")
    public Long getOrderId() {
        return orderId;
    }

    @JsonProperty("total_price")
    public double getTotalPrice() {
        return totalPrice;
    }

    @JsonProperty("order_date")
    public String getOrderDate() {
        return orderDate;
    }

    @JsonProperty("order_items")
    public List<OrderItemDTO> getOrderItems() {
        return orderItems;
    }

    @JsonProperty("user")
    public UserDTO getUserDTO() {
        return userDTO;
    }
}
