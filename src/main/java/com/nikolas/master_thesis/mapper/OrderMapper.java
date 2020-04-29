package com.nikolas.master_thesis.mapper;

import com.nikolas.master_thesis.api.OrderDTO;
import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderMapper implements RowMapper<OrderDTO> {

    @Override
    public OrderDTO map(ResultSet rs, StatementContext ctx) throws SQLException {

        return null;
    }
}
