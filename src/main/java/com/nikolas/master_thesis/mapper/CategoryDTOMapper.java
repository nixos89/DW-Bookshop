package com.nikolas.master_thesis.mapper;

import com.nikolas.master_thesis.api.CategoryDTO;
import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;

import java.sql.ResultSet;
import java.sql.SQLException;


public class CategoryDTOMapper implements RowMapper<CategoryDTO> {

    @Override
    public CategoryDTO map(ResultSet rs, StatementContext ctx) throws SQLException {
        return new CategoryDTO(rs.getLong("category_id"), rs.getString("name"), rs.getBoolean("is_deleted"));
    }
}
