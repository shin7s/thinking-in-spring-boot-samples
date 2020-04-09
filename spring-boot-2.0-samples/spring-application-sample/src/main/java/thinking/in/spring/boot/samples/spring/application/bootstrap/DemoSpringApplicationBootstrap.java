package thinking.in.spring.boot.samples.spring.application.bootstrap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

@EnableAutoConfiguration
public class DemoSpringApplicationBootstrap {
	public static void main(String[] args) {
		SpringApplication.run(DemoSpringApplicationBootstrap.class, args);
	}
}
