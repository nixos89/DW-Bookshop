package com.nikolas.master_thesis.core;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

public class Order {

    private Long orderId;
    private double total;
    @Temporal(TemporalType.TIMESTAMP) // TODO: research this type of annotation for JDBi3 and PostgreSQL
    private Date orderDate;
    private Set<OrderItem> orderItems; // one-to-many
    private User user; // many-to-one

    public Order() {
    }

    public Order(double total, Date orderDate) {
        this.total = total;
        this.orderDate = orderDate;
    }

    public Order(double total, Date orderDate, User user) {
        this.total = total;
        this.orderDate = orderDate;
        this.user = user;
    }

    public Order(Long orderId, double total, Date orderDate, User user) {
        this.orderId = orderId;
        this.total = total;
        this.orderDate = orderDate;
        this.user = user;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public Set<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(Set<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
