package com.nikolas.master_thesis.api;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class OrderDTO {
    private Long orderId;
    private double totalPrice;
    private String orderDate;
    private List<OrderItemDTO> orderItems;
    private UserDTO userDTO;


}
