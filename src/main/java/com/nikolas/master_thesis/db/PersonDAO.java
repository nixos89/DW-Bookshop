package com.nikolas.master_thesis.db;

import com.nikolas.master_thesis.api.PersonDTO;
import com.nikolas.master_thesis.core.Person;
import com.nikolas.master_thesis.mapper.PersonDTOMapper;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;
import org.jdbi.v3.sqlobject.statement.UseRowMapper;

import java.util.List;

/* TODO: implement AbstractDAO interface properly with MANUALLY typing queries using JDBI3
    (DON'T use deprecated JDBI)! Examples:
 *  1) http://jdbi.org/#_postgresql
 *  2) http://jdbi.org/#_annotations_and_inheritance
 *  3) http://jdbi.org/#_upgrading_from_v2_to_v3 - for NEW version JDBi3 to be implemented/methods named properly
 *  */
public interface PersonDAO {

    @RegisterBeanMapper(Person.class)
    @SqlUpdate("CREATE TABLE IF NOT EXISTS person (person_id BIGSERIAL PRIMARY KEY, first_name VARCHAR(30), last_name VARCHAR(30))")
    void createPersonTable();

    @UseRowMapper(PersonDTOMapper.class)
    @SqlQuery("SELECT person_id, first_name, last_name FROM person WHERE person_id = :id")
    PersonDTO findPersonById(@Bind("id") long id);

    // implement Mapper classes/interfaces (FIND OUT what they are!!!) e.g. RowMapper and etc.
    @UseRowMapper(PersonDTOMapper.class)
    @SqlQuery("SELECT first_name FROM person WHERE person_id = :id")
    String findPersonFirstNameById(@Bind("id") long id);

    @UseRowMapper(PersonDTOMapper.class)
    @SqlQuery("SELECT person_id, first_name, last_name FROM person")
    List<PersonDTO> findAllPersons();

    // TODO: Check the difference between @RegisterBeanMapper and @UseRowMapper ??? Is @RegisterBeanMapper correctly used here?
    // source: http://jdbi.org/#_annotated_methods
    @UseRowMapper(PersonDTOMapper.class)
    @GetGeneratedKeys
    @SqlUpdate("INSERT INTO person(first_name, last_name ) VALUES(? , ?)")
    PersonDTO savePerson(String firstName, String lastName);

    @GetGeneratedKeys
    @SqlUpdate("INSERT INTO person(first_name, last_name) VALUES(? , ?)")
    int savePersonInt(String firstName, String lastName);

    @SqlUpdate("UPDATE person SET first_name = :firstName, last_name = :lastName WHERE person_id = :id")
    boolean updatePerson(@Bind("id") Long personId, @Bind("firstName") String firstName, @Bind("lastName") String lastName);

    @SqlUpdate("DELETE FROM person WHERE person_id = :id")
    boolean deletePerson(@Bind("id") Long personId);


}
