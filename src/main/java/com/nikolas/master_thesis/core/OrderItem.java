package com.nikolas.master_thesis.core;


import java.util.Objects;

public class OrderItem {

    private Long orderItemId;
    private int amount;
    // FIXME: change to ID references form object references
    private Book book; // many-to-one
    private Order order; // many-to-one

    public OrderItem() {
    }

    public OrderItem(Long orderItemId, int amount, Book book, Order order) {
        this.orderItemId = orderItemId;
        this.amount = amount;
        this.book = book;
        this.order = order;
    }

    public Long getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(Long orderItemId) {
        this.orderItemId = orderItemId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderItem orderItem = (OrderItem) o;
        return amount == orderItem.amount &&
                orderItemId.equals(orderItem.orderItemId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderItemId, amount);
    }
}
