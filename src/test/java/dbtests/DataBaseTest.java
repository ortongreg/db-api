package dbtests;

import org.junit.Before;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class DataBaseTest {
    JdbcTemplate jdbcTemplate;
    private PlatformTransactionManager transactionManager;

    private static final List<String> PERSON_COLS = Arrays.asList("Name");
    private static final List<String> LOCATION_COLS = Arrays.asList("NAME");
    private static final List<String> SCHEDULE_COLS = Arrays.asList("PERSON", "DATE", "HOURS");
    private static final List<String> EMPLOYMENT_HIST_COLS = Arrays.asList("PERSON","WAGE","LOCATION","STARTDATE","ENDDATE");
    private static final DateTimeFormatter dtf = DateTimeFormatter.ISO_LOCAL_DATE;

    @Before
    public void setUp() throws SQLException {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://127.0.0.1/dbapi?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC&useSSL=false");
        dataSource.setUsername("testuser");
        dataSource.setPassword("password");

        transactionManager = new DataSourceTransactionManager(dataSource);

        jdbcTemplate = new JdbcTemplate(dataSource);
        transactionManager.getTransaction(new DefaultTransactionDefinition());
    }

    void insert(String tableName, List<String> cols, String... values){
        String columns = "("+String.join(",", cols)+")";
        String valuesPart = "('" + String.join("','", values) +"')";
        String sql = "INSERT INTO " + tableName + " " + columns +" VALUES " + valuesPart + ";";
        jdbcTemplate.execute(sql);
    }

    List<Map<String, Object>> queryForList(String query){
        return jdbcTemplate.queryForList(query);
    }

    int queryForInt(String query){
        return jdbcTemplate.queryForObject(query, Integer.class);
    }

    void insertPerson(String name){
        insert("dbo.Person", PERSON_COLS, name);
    }

    void insertWorkSchedule(String name, LocalDate date, int hours){
        String personId = personId(name);
        insert("dbo.WorkSchedule", SCHEDULE_COLS, personId, dtf.format(date.plusDays(1)), String.valueOf(hours));
        Object o = queryForList("select * from dbo.WorkSchedule");
        System.out.println("");
    }

    private String personId(String name){
        String sql = "SELECT * FROM dbo.Person WHERE NAME = '"+name+"'";
        Object personId = jdbcTemplate.queryForMap(sql).get("ID");
        return personId.toString();
    }

    void insertEmploymentHistory(String name, int hourlyWage, String locationName, String startDt, String endDt){
        String personId = personId(name);
        String locationId = locationId(locationName);
        if(endDt != null) {
            insert("dbo.EmploymentHistory", EMPLOYMENT_HIST_COLS, personId, String.valueOf(hourlyWage),
                    locationId, startDt, endDt);
        }else{
            insert("dbo.EmploymentHistory", EMPLOYMENT_HIST_COLS.subList(0, EMPLOYMENT_HIST_COLS.size() -1), personId, String.valueOf(hourlyWage),
                    locationId, startDt);
        }
    }

    void insertEmploymentHistory(String name, int hourlyWage, String locationName, LocalDate startDt, LocalDate endDt){
        String endDtStr = endDt == null ? null : endDt.format(dtf);
        insertEmploymentHistory(name, hourlyWage, locationName, startDt.format(dtf), endDtStr);
    }

    void insertLocation(String locationName){
        insert("dbo.Location", LOCATION_COLS, locationName);
    }

    private String locationId(String locationName){
        String sql = "SELECT * FROM dbo.Location WHERE NAME = '"+locationName +"'";
        Object locationId = jdbcTemplate.queryForMap(sql).get("ID");
        return locationId.toString();
    }
}