package com.nikolas.master_thesis.api;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemDTO {
    private Long orderItemId;
    private int amount;
    private BookDTO book;
    private Long orderId;
    private double totalOrderItemPrice;

}
