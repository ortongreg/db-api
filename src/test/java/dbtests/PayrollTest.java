package dbtests;

import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.util.List;

public class PayrollTest {

    @Test
    public void withConnection(){
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://127.0.0.1/dbapi?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC&useSSL=false");
        dataSource.setUsername("testuser");
        dataSource.setPassword("password");
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        List l = jdbcTemplate.queryForList("select * from dbo.Person");

        assert 0 == l.size();

    }

}