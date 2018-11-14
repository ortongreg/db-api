package dbtests;

import org.junit.Test;
import java.util.Map;

import java.util.List;

public class PersonTest extends DataBaseTest {

    @Test
    public void givenNoPersons_WhenSelectAllPerson_thenRetrunZeroPersons()  {
        List l = queryForList("select * from dbo.Person");
        assert 0 == l.size();
    }

    @Test
    public void givenOnePersons_WhenSelectAllPerson_thenRetrunOnePersons()  {
        insertPerson("Bob Jones");

        List<Map<String, java.lang.Object>> result = queryForList("select * from dbo.Person");
        assert 1 == result.size();
        assert "Bob Jones".equals(result.get(0).get("NAME"));
    }

}