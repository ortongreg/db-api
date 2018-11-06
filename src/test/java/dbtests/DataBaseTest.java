package dbtests;

import org.junit.Before;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class DataBaseTest {
    JdbcTemplate jdbcTemplate;
    private PlatformTransactionManager transactionManager;

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

}