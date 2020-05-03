package com.nikolas.master_thesis.mapper;

import com.nikolas.master_thesis.examples.Account;
import com.nikolas.master_thesis.examples.UserA;
import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

public class AccountMapper implements RowMapper<Account> {

    private Account account;

    /* TODO: change implementation! DON'T do it like this -> it's NOT THREAD SAFE !!!
    *   Examples:
    *   1) https://sanaulla.info/2015/01/17/getting-rid-of-getters-and-setters-in-your-pojo/
    *   2) https://stackoverflow.com/questions/24337100/how-to-create-a-one-to-many-relationship-with-jdbi-sql-object-api
    *  */
    @Override
    public Account map(ResultSet rs, StatementContext ctx) throws SQLException {
        account = new Account(rs.getInt("a_id"), rs.getString("a_name"), new LinkedList<UserA>());
        UserA userA = new UserA(rs.getInt("u_id"), rs.getString("u_name"));

        if (userA.getId() > 0) {
            account.getUserAS().add(userA);
        }
        return account;
    }
}
