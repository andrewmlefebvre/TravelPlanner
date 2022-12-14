package Spring;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.filter.CommonsRequestLoggingFilter;


@SpringBootApplication
@ComponentScan(basePackages = "Rest")
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

}
