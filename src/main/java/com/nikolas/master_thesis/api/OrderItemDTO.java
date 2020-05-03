package com.nikolas.master_thesis.api;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class OrderItemDTO {
    private Long orderItemId;
    private int amount;
    private BookDTO book;
    private Long orderId;
    private double totalOrderItemPrice;

}
