package com.nikolas.master_thesis.mapper;

import com.nikolas.master_thesis.core.OrderItem;
import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderItemMapper implements RowMapper<OrderItem> {

    @Override
    public OrderItem map(ResultSet rs, StatementContext ctx) throws SQLException {
        Long orderItemId = rs.getLong("oi_id");
        int amount = rs.getInt("amount");
        Long bookId = rs.getLong("book_id");
        Long orderId = rs.getLong("order_id");

        return new OrderItem();
    }
}
