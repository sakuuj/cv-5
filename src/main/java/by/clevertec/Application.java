package by.clevertec;

import by.clevertec.repository.CakeRepo;
import by.clevertec.repository.CakeRepoImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.HashMap;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public CakeRepo cakeRepo() {
        return new CakeRepoImpl(new HashMap<>());
    }
}
