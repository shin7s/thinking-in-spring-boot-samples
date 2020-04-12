package thinking.in.spring.boot.samples.spring4.jdbc;

import org.springframework.jdbc.core.JdbcTemplate;
import thinking.in.spring.boot.samples.spring4.domain.User;

import javax.sql.DataSource;
import java.sql.Types;

public class UserServiceImpl implements UserService {

    private JdbcTemplate jdbcTemplate;

    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void save(User user) throws Exception {
        jdbcTemplate.update("insert into user(name, age, sex) values (?, ?, ?)",
                new Object[] {user.getName(), user.getAge(), user.getSex()},
                new int[] {Types.VARCHAR, Types.INTEGER, Types.VARCHAR});

        //throw new RuntimeException("aa");
        //不会回滚
        throw new Exception("asd");
    }
}
