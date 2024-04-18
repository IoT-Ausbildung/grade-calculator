package com.example.gradecalculator;

import com.example.gradecalculator.entities.UserType;
import com.example.gradecalculator.repository.UserTypeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
@Configuration
public class GradeCalculatorApplication {

	public static void main(String[] args) {
		SpringApplication.run(GradeCalculatorApplication.class, args);
	}

	@Bean
	public CommandLineRunner demoData(UserTypeRepository repo) {
		return args -> {
			if (repo.findByName("Auszubildender") == null) {
				UserType auszubildender = new UserType();
				auszubildender.setName("Auszubildender");
				repo.save(auszubildender);
			}

			if (repo.findByName("Ausbilder") == null) {
				UserType ausbilder = new UserType();
				ausbilder.setName("Ausbilder");
				repo.save(ausbilder);
			}

			if (repo.findByName("Werkstudent") == null) {
				UserType werkstudent = new UserType();
				werkstudent.setName("Werkstudent");
				repo.save(werkstudent);
			}
		};
	}
}
