package thinking.in.spring.boot.samples.spring4.aop;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AopTest {
    public static void main(String[] args) {
        ApplicationContext ac = new ClassPathXmlApplicationContext("spring/aop.xml");
        ITestBean test = (ITestBean) ac.getBean("test");
        test.test();
    }
}
