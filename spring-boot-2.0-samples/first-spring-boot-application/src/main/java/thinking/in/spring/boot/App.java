package thinking.in.spring.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Hello world!
 */
@RestController
@SpringBootApplication
public class App {

    @RequestMapping("/")
    public String index() {
        return "Welcome , My Buddy!";
    }

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
    
	@EventListener(WebServerInitializedEvent.class)
	public void onWebServerReader(WebServerInitializedEvent event) {
		System.out.println(event.getWebServer().getClass().getName());
	}
}