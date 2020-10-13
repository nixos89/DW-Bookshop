package com.nikolas.master_thesis.db;

import com.nikolas.master_thesis.core.Role;
import com.nikolas.master_thesis.core.User;
import com.nikolas.master_thesis.reducers.UserRoleReducer;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;
import org.jdbi.v3.sqlobject.statement.UseRowReducer;


public interface UserDAO {

    @RegisterBeanMapper(User.class)
    @SqlUpdate("CREATE TABLE IF NOT EXISTS Users (user_id BIGSERIAL PRIMARY KEY, first_name VARCHAR(30), last_name VARCHAR(30), " +
            "email VARCHAR(30), username VARCHAR(15), password VARCHAR(255), role_id BIGINT REFERENCES Roles(role_id) )")
    void createUserTable();

    @RegisterBeanMapper(value = User.class, prefix = "u")
    @RegisterBeanMapper(value = Role.class, prefix = "r")
    @SqlQuery("SELECT u.user_id u_user_id, u.first_name, u.last_name, u.email, u.username, u.password, r.role_id r_role_id " +
            "FROM users u LEFT JOIN roles AS r ON u.role_id = r.role_id WHERE u.username LIKE :username")
    @UseRowReducer(UserRoleReducer.class)
    User findUserByUsername(@Bind("username") String username);
}
