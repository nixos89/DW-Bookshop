package com.nikolas.master_thesis.db;

import com.nikolas.master_thesis.api.UserADTO;
import com.nikolas.master_thesis.core.Role;
import com.nikolas.master_thesis.core.User;
import com.nikolas.master_thesis.reducers.UserRoleReducer;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;
import org.jdbi.v3.sqlobject.statement.UseRowReducer;

import java.util.List;

public interface UserDAO {

    @RegisterBeanMapper(User.class)
    @SqlUpdate("CREATE TABLE IF NOT EXISTS Users (user_id BIGSERIAL PRIMARY KEY, first_name VARCHAR(30), last_name VARCHAR(30), " +
            "email VARCHAR(30), username VARCHAR(15), password VARCHAR(255), role_id BIGINT REFERENCES Roles(role_id) )")
    void createUserTable();


    @RegisterBeanMapper(value = User.class, prefix = "u")
    @RegisterBeanMapper(value = Role.class, prefix = "r")
    @SqlQuery("SELECT u")
    @UseRowReducer(UserRoleReducer.class)
    List<UserADTO> getAllUsers();
}
