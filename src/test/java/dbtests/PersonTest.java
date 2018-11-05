package dbtests;

import org.junit.Test;
import java.util.List;

public class PersonTest extends DataBaseTest {

    @Test
    public void givenNoPersons_WhenSelectAllPerson_thenRetrunZeroPersons()  {

        List l = jdbcTemplate.queryForList("select * from dbo.Person");

        assert 0 == l.size();
    }

    @Test
    public void givenOnePersons_WhenSelectAllPerson_thenRetrunOnePersons()  {

        jdbcTemplate.execute("insert into dbo.Person (Name) values ('Bob Jones')");
        List l = jdbcTemplate.queryForList("select * from dbo.Person");

        assert 1 == l.size();
    }

}