package thinking.in.spring.boot.samples.spring4.bootstrap;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import thinking.in.spring.boot.samples.spring4.domain.EntityA;

public class CpxacTest {
    public static void main(String[] args) {
        ApplicationContext ac = new ClassPathXmlApplicationContext("spring/context.xml");
        EntityA ea = (EntityA)ac.getBean("ea");
        System.out.println(ea.getB() == null);
    }
}
