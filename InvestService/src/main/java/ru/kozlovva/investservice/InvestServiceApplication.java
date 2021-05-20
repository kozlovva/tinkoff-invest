package ru.kozlovva.investservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableMongoRepositories
@SpringBootApplication
@EnableScheduling
public class InvestServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(InvestServiceApplication.class, args);
    }

}
