package com.nikolas.master_thesis.mapper;

import com.nikolas.master_thesis.examples.PersonDTO;
import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PersonDTOMapper implements RowMapper<PersonDTO> {

    @Override
    public PersonDTO map(ResultSet rs, StatementContext ctx) throws SQLException {
        System.out.println("============= rs.toString(): " + rs.toString() + " =============");
        return new PersonDTO(rs.getLong("person_id"), rs.getString("first_name"), rs.getString("last_name"));
    }

}
