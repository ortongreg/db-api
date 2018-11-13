package dbtests;

import org.junit.Assert;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class Payroll_BulkLoad_Test extends DataBaseTest {
    private static final LocalDate OCT_26 = LocalDate.of(2018, 10,26);
    private static final LocalDate NOV_9 = LocalDate.of(2018, 11,9);

    @Test
    public void givenOTwoPersons_WhenSelectSp_Payroll_thenRetrunTwoRows()  {
        loadDbData();

        List result = payrollQuery(OCT_26, NOV_9);

        String expectation = loadFile("./src/test/resources/goldenmasters/Payroll_goldenmaster.txt").get(0);
        Assert.assertEquals(expectation, result.toString());
    }

    private List<Map<String, Object>> payrollQuery(LocalDate startDate, LocalDate endDate){
        LocalDate ldStart = startDate.plusDays(1);
        LocalDate ldEnd = endDate.plusDays(1);
        DateTimeFormatter dtf = DateTimeFormatter.ISO_LOCAL_DATE;
        return queryForList("call dbapi.sp_Payroll('"+ ldStart.format(dtf) +"','"+ ldEnd.format(dtf) +"')");
    }

    private void loadDbData(){
        List<String> sqls = loadFile("./src/test/resources/testsql/payroll_golden_master_data.sql");
        for (String sql : sqls) {
            executeSql(sql);
        }
    }

    private List<String> loadFile(String filePath){
        List<String> contents = new ArrayList<String>();
        try {
            File f = new File(filePath);

            BufferedReader br = new BufferedReader(new FileReader(f));

            String st;
            while ((st = br.readLine()) != null) {
                if(!st.isEmpty()) contents.add(st);
            }
        }catch(Exception e) {
            throw new RuntimeException(e);
        }
        return contents;
    }

}