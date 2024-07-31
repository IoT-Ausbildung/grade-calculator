package com.example.gradecalculator;

import com.example.gradecalculator.config.DBInitializer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class GradeCalculatorApplication {
    public static void main(String[] args) {
        SpringApplication.run(GradeCalculatorApplication.class, args);
    }

    @Bean
    public CommandLineRunner initializeDatabase(DBInitializer initializer) {
        return args ->{
            initializer.seedData();
        };
    }
}