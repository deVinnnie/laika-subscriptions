package be.mira.jongeren.mailinglist;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        System.out.println("Hello World");
        System.out.println(System.getProperty("JDBC_DATABASE_URL"));
        SpringApplication.run(Application.class, args);
    }

}
