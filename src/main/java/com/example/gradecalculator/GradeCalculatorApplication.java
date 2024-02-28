package com.example.gradecalculator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class GradeCalculatorApplication {

	public static void main(String[] args) {
		SpringApplication.run(GradeCalculatorApplication.class, args);
	}

}
