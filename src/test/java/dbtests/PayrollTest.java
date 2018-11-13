package dbtests;

import org.junit.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

public class PayrollTest extends DataBaseTest {
    private static final LocalDate OCT_25 = LocalDate.of(2018, 10,25);
    private static final LocalDate OCT_26 = LocalDate.of(2018, 10,26);
    private static final LocalDate OCT_27 = LocalDate.of(2018, 10,27);
    private static final LocalDate NOV_2 = LocalDate.of(2018, 11,2);
    private static final LocalDate NOV_3 = LocalDate.of(2018, 11,3);
    private static final LocalDate NOV_4 = LocalDate.of(2018, 11,4);
    private static final LocalDate NOV_9 = LocalDate.of(2018, 11,9);

    @Test
    public void givenNoWorkScheduled_WhenSp_Payroll_thenReturnNoRows()  {
        LocalDate start = LocalDate.of(2018, 11,1);
        LocalDate end = LocalDate.of(2018, 11,5);
        List l = payrollQuery(start, end);

        assert 0 == l.size();
    }

    @Test
    public void givenOnePersons_oneWorkDay_WhenSelectSp_Payroll_thenReturnOneRows()  {
        String name = "Bob Jones";
        String location = "homebase";
        insertPerson(name);
        insertWorkSchedule(name, NOV_2, 8);
        insertLocation(location);
        insertEmploymentHistory(name, 20, location, "2018-10-26", "2018-11-09");

        List result = payrollQuery(OCT_26, NOV_9);
        assert 1 == result.size();
        Map<Object, Object> row = (Map<Object, Object>) result.get(0);
        assert name.equals(row.get("NAME"));
        assert "8".equals(row.get("HOURS").toString());
        assert "2018-10-26".equals(row.get("START_DATE").toString());
        assert "2018-11-09".equals(row.get("END_DATE").toString());
        assert "160.0".equals(row.get("AMOUNT").toString());
    }

    @Test
    public void givenOnePersons_MultipleWorkDay_WhenSelectSp_Payroll_thenReturnOneRows()  {
        String name = "Bob Jones";
        String location = "homebase";
        insertPerson(name);
        insertWorkSchedule(name, NOV_2, 8);
        insertWorkSchedule(name, NOV_3, 4);
        insertLocation(location);
        insertEmploymentHistory(name, 20, location, "2018-10-26", "2018-11-09");

        List result = payrollQuery(OCT_26, NOV_9);
        assert 1 == result.size();
        Map<Object, Object> row = (Map<Object, Object>) result.get(0);
        assert name.equals(row.get("NAME"));
        assert "12".equals(row.get("HOURS").toString());
        assert "2018-11-09".equals(row.get("END_DATE").toString());
        assert "240.0".equals(row.get("AMOUNT").toString());
    }

    @Test
    public void givenOnePersons_MultipleWorkDay_OneBefore_WhenSelectSp_Payroll_thenReturnDaysInPayPeriod()  {
        String name = "Bob Jones";
        String location = "homebase";
        insertPerson(name);
        insertWorkSchedule(name, OCT_25, 8);
        insertWorkSchedule(name, OCT_26, 4);
        insertLocation(location);
        insertEmploymentHistory(name, 20, location, "2018-10-26", "2018-11-09");

        List result = payrollQuery(OCT_26, NOV_9);
        assert 1 == result.size();
        Map<Object, Object> row = (Map<Object, Object>) result.get(0);
        assert name.equals(row.get("NAME"));
        assert "4".equals(row.get("HOURS").toString());
        assert "80.0".equals(row.get("AMOUNT").toString());
    }

    @Test
    public void givenOTwoPersons_WhenSelectSp_Payroll_thenRetrunTwoRows()  {
        String location = "homebase";
        insertLocation(location);

        String bob = "Bob Jones";
        insertPerson(bob);
        insertWorkSchedule(bob, NOV_2, 8);
        insertEmploymentHistory(bob, 20, location, "2018-10-26", null);

        String sally = "Sally Smith";
        insertPerson(sally);
        insertWorkSchedule(sally, NOV_4, 4);
        insertEmploymentHistory(sally, 22, location, "2017-01-01", null);

        List result = payrollQuery(OCT_26, NOV_9);
        assert 2 == result.size();
        Map<Object, Object> bobRow = (Map<Object, Object>) result.get(0);
        assert bob.equals(bobRow.get("NAME"));
        assert "8".equals(bobRow.get("HOURS").toString());
        assert "2018-11-09".equals(bobRow.get("END_DATE").toString());
        assert "160.0".equals(bobRow.get("AMOUNT").toString());
        Map<Object, Object> sallyRow = (Map<Object, Object>) result.get(1);
        assert sally.equals(sallyRow.get("NAME"));
        assert "4".equals(sallyRow.get("HOURS").toString());
        assert "2018-11-09".equals(sallyRow.get("END_DATE").toString());
        assert "88.0".equals(sallyRow.get("AMOUNT").toString());
    }

    private List<Map<String, Object>> payrollQuery(LocalDate startDate, LocalDate endDate){
        LocalDate ldStart = startDate.plusDays(1);
        LocalDate ldEnd = endDate.plusDays(1);
        DateTimeFormatter dtf = DateTimeFormatter.ISO_LOCAL_DATE;
        return queryForList("call dbapi.sp_Payroll('"+ ldStart.format(dtf) +"','"+ ldEnd.format(dtf) +"')");
    }

}