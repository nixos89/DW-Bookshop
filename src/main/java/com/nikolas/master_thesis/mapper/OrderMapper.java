package com.nikolas.master_thesis.mapper;

import com.nikolas.master_thesis.api.OrderDTO;
import com.nikolas.master_thesis.core.Order;
import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class OrderMapper implements RowMapper<Order> {

    @Override
    public Order map(ResultSet rs, StatementContext ctx) throws SQLException {
        // order_id, total, order_date, user_id
        final Long orderId = rs.getLong("order_id");
        double total = rs.getDouble("total");
        Date orderDate = rs.getDate("order_date");

        return null;
    }
}
