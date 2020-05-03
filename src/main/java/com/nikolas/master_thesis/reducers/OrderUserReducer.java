package com.nikolas.master_thesis.reducers;

import com.nikolas.master_thesis.core.Order;
import com.nikolas.master_thesis.core.User;
import org.jdbi.v3.core.result.LinkedHashMapRowReducer;
import org.jdbi.v3.core.result.RowView;

import java.util.Map;

public class OrderUserReducer implements LinkedHashMapRowReducer<Long, Order> {

    @Override
    public void accumulate(Map<Long, Order> container, RowView rowView) {
        final Order order = container.computeIfAbsent(
                rowView.getColumn("o_order_id", Long.class),
                id -> rowView.getRow(Order.class));

        if (rowView.getColumn("u_role_id", Long.class) != null) {
            order.setUser(rowView.getRow(User.class));
        }
    }
}
