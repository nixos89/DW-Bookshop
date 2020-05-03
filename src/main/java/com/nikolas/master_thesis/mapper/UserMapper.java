package com.nikolas.master_thesis.mapper;

import com.nikolas.master_thesis.core.User;
import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper implements RowMapper<User> {

    @Override
    public User map(ResultSet rs, StatementContext ctx) throws SQLException {
        final Long userId = rs.getLong("user_id");
        String firstName = rs.getString("first_name");
        String lastName = rs.getString("last_name");
        String email = rs.getString("email");
        String username = rs.getString("username");
        Long roleId = rs.getLong("role_id");
//        return new User(userId, firstName, lastName, email, username, roleId); // TODO must use UserRoleReducer
        return null;
    }
}
