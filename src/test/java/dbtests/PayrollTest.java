package dbtests;

import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.sql.SQLException;
import java.util.List;

public class PayrollTest {
    private JdbcTemplate jdbcTemplate;
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

    @Test
    public void givenNoPersons_WhenSelectAllPerson_thenRetrunZeroPersons()  {

        List l = jdbcTemplate.queryForList("select * from dbo.Person");

        assert 0 == l.size();
    }

    @Test
    public void givenOnePersons_WhenSelectAllPerson_thenRetrunOnePersons()  {

        jdbcTemplate.execute("insert into dbo.Person (Name) values ('Bob Jones')");
        List l = jdbcTemplate.queryForList("select * from dbo.Person");

        assert 1 == l.size();
    }

}