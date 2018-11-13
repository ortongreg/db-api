package dbtests;

import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.Assert.assertEquals;

public class WageTest extends DataBaseTest {
    private DateTimeFormatter dtf = DateTimeFormatter.ISO_LOCAL_DATE;
    private static final LocalDate OCT_1 = LocalDate.of(2018, 10,1);
    private static final LocalDate NOV_2 = LocalDate.of(2018, 11,2);
    private static final LocalDate NOV_3 = LocalDate.of(2018, 11,3);
    private static final LocalDate NOV_4 = LocalDate.of(2018, 11,4);
    private static final LocalDate DEC_1 = LocalDate.of(2018, 12,1);
    private static final String BOB = "Bob Jones";
    private static final String HOME_BASE = "Home Base";

    @Before
    public void setUp() throws SQLException {
        super.setUp();
        insertPerson(BOB);
        insertLocation(HOME_BASE);
    }

    @Test
    public void givenWageWithNoEndDt_WhenFn_Wage_thenReturnWage()  {
        insertEmploymentHistory(BOB, 10, HOME_BASE, OCT_1, null);

        int result = fn_Wage(BOB, OCT_1);

        assertEquals(10, result);
    }

    @Test
    public void givenWageWitEndDt_WhenFn_Wage_thenReturnWage()  {
        insertEmploymentHistory(BOB, 15, HOME_BASE, OCT_1, DEC_1);

        int result = fn_Wage(BOB, OCT_1);

        assertEquals(15, result);
    }

    private int fn_Wage(String personName, LocalDate date){
//        LocalDate ldStart = date.plusDays(1);
        return queryForInt("select dbapi.fn_Wage('"+personName +"','"+ date.format(dtf) +"')");
    }

}