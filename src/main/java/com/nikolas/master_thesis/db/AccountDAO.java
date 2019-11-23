package com.nikolas.master_thesis.db;

import com.nikolas.master_thesis.api.AccountDTO;
import com.nikolas.master_thesis.core.Account;
import com.nikolas.master_thesis.core.UserA;
import com.nikolas.master_thesis.mapper.AccountDTOMapper;
import com.nikolas.master_thesis.mapper.AccountMapper;
import com.nikolas.master_thesis.reducers.AccountUserReducer;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.*;

import java.util.List;

public interface AccountDAO {

    @RegisterBeanMapper(Account.class)
    @SqlUpdate("CREATE TABLE IF NOT EXISTS Account (id SERIAL PRIMARY KEY, name VARCHAR(30))")
    void createAccountTable();

    @RegisterBeanMapper(value = Account.class, prefix = "a")
    @RegisterBeanMapper(value = UserA.class, prefix = "u")
    @SqlQuery("SELECT a.id a_id, a.name a_name, u.id u_id, u.name u_name " +
            "FROM Account a " +
            "LEFT JOIN Users_a u ON u.account_id = a.id")
    @UseRowReducer(AccountUserReducer.class)
    List<AccountDTO> getAllAccounts();

    @UseRowMapper(AccountDTOMapper.class)
    @SqlQuery("SELECT Account.id, Account.name, Users_a.name as u_name FROM Account " +
            "LEFT JOIN Users_a ON Users_a.account_id = Account.id WHERE Account.id = :id")
    AccountDTO getAccountDTOById(@Bind("id") int id);

    @UseRowMapper(AccountMapper.class) // PAY ATTENTION to THIS class (it's NOT a DTO) !!!
    @SqlQuery("SELECT Account.id AS a_id, Account.name AS a_name, Users_a.id AS u_id, Users_a.name AS u_name " +
            "FROM Account LEFT JOIN Users_a ON Users_a.account_id = Account.id WHERE Account.id = :id")
    Account getAccountById(@Bind("id") int id);

    // source: https://stackoverflow.com/questions/24337100/how-to-create-a-one-to-many-relationship-with-jdbi-sql-object-api/52586791#52586791
    // switched from Account/UserA to AccountDTO/UserADTO classes
    @RegisterBeanMapper(value = Account.class, prefix = "a")
    @RegisterBeanMapper(value = UserA.class, prefix = "u")
    @SqlQuery("SELECT a.id a_id, a.name a_name, u.id u_id, u.name u_name " +
            "FROM Account a " +
            "LEFT JOIN Users_a u ON u.account_id = a.id " +
            "WHERE a.id = :id")
    @UseRowReducer(AccountUserReducer.class)
    Account getAccount(@Bind("id") int id);

    @UseRowMapper(AccountDTOMapper.class)
    @GetGeneratedKeys
    @SqlUpdate("INSERT INTO Account (name) VALUES (?)")
    AccountDTO saveAccount(String name);

    @SqlUpdate("DELETE FROM account WHERE id = :id")
    boolean deleteAccount(@Bind("id") int id);

}
