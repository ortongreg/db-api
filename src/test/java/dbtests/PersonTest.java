package dbtests;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class PersonTest extends DataBaseTest {

    @Test
    public void givenNoPersons_WhenSelectAllPerson_thenRetrunZeroPersons()  {
        List l = queryForList("select * from dbo.Person");
        assert 0 == l.size();
    }

    @Test
    public void givenOnePersons_WhenSelectAllPerson_thenRetrunOnePersons()  {
        insert("dbo.Person", Arrays.asList("Name"), "Bob Jones");

        List l = queryForList("select * from dbo.Person");
        assert 1 == l.size();
    }

}