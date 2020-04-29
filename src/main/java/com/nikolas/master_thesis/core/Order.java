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
    @Temporal(TemporalType.TIMESTAMP) // TODO: research this type of annotation for JDBi3 and PostgreSQL
    private Date orderDate;
    private Set<OrderItem> orderItems; // one-to-many
    private User user; // many-to-one

    public Order(Long orderId, double total, Date orderDate, User user) {
        this.orderId = orderId;
        this.total = total;
        this.orderDate = orderDate;
        this.user = user;
    }

}
