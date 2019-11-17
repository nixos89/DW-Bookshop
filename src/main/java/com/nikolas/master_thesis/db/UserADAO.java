package com.nikolas.master_thesis.db;

import com.nikolas.master_thesis.api.UserADTO;
import com.nikolas.master_thesis.core.UserA;
import com.nikolas.master_thesis.mapper.UserADTOMapper;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.*;

import java.util.List;

public interface UserADAO {

    @RegisterBeanMapper(UserA.class)
    @SqlUpdate("CREATE TABLE IF NOT EXISTS Users_a (id SERIAL PRIMARY KEY, name VARCHAR(30), account_id INTEGER REFERENCES Account(id) )")
    void createUserATable();

    @UseRowMapper(UserADTOMapper.class)
    @SqlQuery("SELECT id, name, account_id FROM Users")
    List<UserADTO> getAllUsers();

    @UseRowMapper(UserADTOMapper.class)
    @SqlQuery("SELECT Users_a.id, Users_a.name, Users_a.account_id FROM Users_a LEFT JOIN Account ON Account.id = Users_a.account_id WHERE Users_a.account_id = :accountId")
    List<UserADTO> getUsersForAccount(@Bind("accountId") Integer accountId);

    @UseRowMapper(UserADTOMapper.class)
    @SqlQuery("SELECT * FROM Users_a WHERE id = :id")
    UserADTO getUserById(@Bind("id") int id);

    @UseRowMapper(UserADTOMapper.class)
    @GetGeneratedKeys
    @SqlUpdate("INSERT INTO Users_a (name, account_id) VALUES (?, ?)")
    UserADTO saveUser(String name, int account_id);

    @SqlUpdate("DELETE FROM Users_a WHERE id = :id")
    public boolean deleteUser(@Bind("id") int id);
}
