package com.nikolas.master_thesis.reducers;

import com.nikolas.master_thesis.core.Book;
import com.nikolas.master_thesis.core.OrderItem;
import org.jdbi.v3.core.result.LinkedHashMapRowReducer;
import org.jdbi.v3.core.result.RowView;

import java.util.Map;

public class OrderItemBookReducer  implements LinkedHashMapRowReducer<Long, OrderItem> {

    @Override
    public void accumulate(Map<Long, OrderItem> container, RowView rowView) {
        OrderItem orderItem = container.computeIfAbsent(
                rowView.getColumn("oi_order_item_id", Long.class),
                id -> rowView.getRow(OrderItem.class));

        if (rowView.getColumn("b_book_id", Long.class) != null) {
            orderItem.setBook(rowView.getRow(Book.class));
        }
    }
}
