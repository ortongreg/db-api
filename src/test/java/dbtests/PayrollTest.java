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

}