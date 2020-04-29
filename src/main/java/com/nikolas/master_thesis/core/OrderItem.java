package com.nikolas.master_thesis.core;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"book", "order"})
public class OrderItem {

    private Long orderItemId;
    private int amount;
    private Book book; // many-to-one
    private Order order; // many-to-one

}
