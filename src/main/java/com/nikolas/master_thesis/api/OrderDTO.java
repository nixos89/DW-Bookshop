package com.nikolas.master_thesis.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {
    private Long orderId;
    private double totalPrice;
    private String orderDate;
    private List<OrderItemDTO> orderItems;
    @JsonProperty("user")
    private UserDTO userDTO;


}
