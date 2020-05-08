package com.nikolas.master_thesis.core;

import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"orderItems"})
public class Order {

    private Long orderId;
    private double total;
    @Temporal(TemporalType.TIMESTAMP)
    private Date orderDate;
    private Set<OrderItem> orderItems;
    private User user;

    public Order(Long orderId, double total, Date orderDate, User user) {
        this.orderId = orderId;
        this.total = total;
        this.orderDate = orderDate;
        this.user = user;
    }

}
