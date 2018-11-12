package dbtests;

import org.junit.Test;
import java.util.List;
import java.util.Map;

public class PayrollTest extends DataBaseTest {

    @Test
    public void givenNoWorkScheduled_WhenSp_Payroll_thenRetrunNoRows()  {
        List l = queryForList("call dbapi.sp_Payroll('2018-11-01')");
        assert 0 == l.size();
    }

    @Test
    public void givenOnePersons_oneWorkDay_WhenSelectSp_Payroll_thenRetrunOneRows()  {
        String name = "Bob Jones";
        String location = "homebase";
        insertPerson(name);
        insertWorkSchedule(name, "2018-11-02", 8);
        insertLocation(location);
        insertEmploymentHistory(name, 20, location, "2018-10-26", "2018-11-09");

        List result = queryForList("call dbapi.sp_Payroll('2018-11-09')");
        assert 1 == result.size();
        Map<Object, Object> row = (Map<Object, Object>) result.get(0);
        assert name.equals(row.get("NAME"));
        assert "8".equals(row.get("HOURS").toString());
        assert "2018-11-09".equals(row.get("END_DATE"));
        assert "160.0".equals(row.get("AMOUNT").toString());
    }

    @Test
    public void givenOnePersons_MultipleWorkDay_WhenSelectSp_Payroll_thenRetrunOneRows()  {
        String name = "Bob Jones";
        String location = "homebase";
        insertPerson(name);
        insertWorkSchedule(name, "2018-11-02", 8);
        insertWorkSchedule(name, "2018-11-03", 4);
        insertLocation(location);
        insertEmploymentHistory(name, 20, location, "2018-10-26", "2018-11-09");

        List result = queryForList("call dbapi.sp_Payroll('2018-11-09')");
        assert 1 == result.size();
        Map<Object, Object> row = (Map<Object, Object>) result.get(0);
        assert name.equals(row.get("NAME"));
        assert "12".equals(row.get("HOURS").toString());
        assert "2018-11-09".equals(row.get("END_DATE"));
        assert "240.0".equals(row.get("AMOUNT").toString());
    }

    @Test
    public void givenOTwoPersons_WhenSelectSp_Payroll_thenRetrunTwoRows()  {
        String location = "homebase";
        insertLocation(location);

        String bob = "Bob Jones";
        insertPerson(bob);
        insertWorkSchedule(bob, "2018-11-02", 8);
        insertEmploymentHistory(bob, 20, location, "2018-10-26", null);

        String sally = "Sally Smith";
        insertPerson(sally);
        insertWorkSchedule(sally, "2018-11-04", 4);
        insertEmploymentHistory(sally, 22, location, "2017-01-01", null);

        List result = queryForList("call dbapi.sp_Payroll('2018-11-09')");
        assert 2 == result.size();
        Map<Object, Object> bobRow = (Map<Object, Object>) result.get(0);
        assert bob.equals(bobRow.get("NAME"));
        assert "8".equals(bobRow.get("HOURS").toString());
        assert "2018-11-09".equals(bobRow.get("END_DATE"));
        assert "160.0".equals(bobRow.get("AMOUNT").toString());
        Map<Object, Object> sallyRow = (Map<Object, Object>) result.get(1);
        assert sally.equals(sallyRow.get("NAME"));
        assert "4".equals(sallyRow.get("HOURS").toString());
        assert "2018-11-09".equals(sallyRow.get("END_DATE"));
        assert "88.0".equals(sallyRow.get("AMOUNT").toString());
    }

}