package thinking.in.spring.boot.samples.spring4.jdbc;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import thinking.in.spring.boot.samples.spring4.domain.User;

public class TxTest {
    public static void main(String[] args) throws Exception {
        ApplicationContext ac = new ClassPathXmlApplicationContext("spring/jdbc.xml");
        UserService userService = (UserService) ac.getBean("userService");
        userService.save(new User(null, "zhangsan", 14, "male"));
    }
}
