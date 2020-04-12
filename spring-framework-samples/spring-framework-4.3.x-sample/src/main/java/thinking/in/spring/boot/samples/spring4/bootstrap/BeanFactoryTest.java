package thinking.in.spring.boot.samples.spring4.bootstrap;

import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;

import thinking.in.spring.boot.samples.spring4.domain.EntityA;
import thinking.in.spring.boot.samples.spring4.domain.User;

public class BeanFactoryTest {

    public static void main(String[] args) {
        DefaultListableBeanFactory bf = new DefaultListableBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(bf);
        reader.loadBeanDefinitions("spring/context.xml");

        EntityA a = (EntityA)bf.getBean("ea");
        System.out.println(a.getB() == null);
    }
}
