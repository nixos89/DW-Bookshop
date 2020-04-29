package com.nikolas.master_thesis.db;

import com.nikolas.master_thesis.core.Role;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

public interface RoleDAO {

    @RegisterBeanMapper(Role.class)
    @SqlUpdate("CREATE TABLE IF NOT EXISTS Roles(role_id BIGSERIAL PRIMARY KEY, name VARCHAR(30))")
    void createTableRole();

}
