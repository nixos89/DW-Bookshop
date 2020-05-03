package com.nikolas.master_thesis.reducers;

import com.nikolas.master_thesis.core.Book;
import com.nikolas.master_thesis.core.Order;
import com.nikolas.master_thesis.core.OrderItem;
import org.jdbi.v3.core.result.LinkedHashMapRowReducer;
import org.jdbi.v3.core.result.RowView;

import java.util.Map;

public class OrderItemOrderReducer implements LinkedHashMapRowReducer<Long, OrderItem> {

    @Override
    public void accumulate(Map<Long, OrderItem> container, RowView rowView) {
        final OrderItem orderItem = container.computeIfAbsent(
                rowView.getColumn("oi_order_item_id", Long.class),
                id -> rowView.getRow(OrderItem.class));

        if (rowView.getColumn("o_order_id", Long.class) != null) {
            orderItem.setOrder(rowView.getRow(Order.class));
        }

        if (rowView.getColumn("b_book_id", Long.class) != null) {
            orderItem.setBook(rowView.getRow(Book.class));
        }
    }
}
