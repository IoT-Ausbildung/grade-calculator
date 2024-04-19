package com.example.gradecalculator;

import com.example.gradecalculator.entities.UserType;
import com.example.gradecalculator.repository.UserTypeRepository;
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
	public CommandLineRunner demoData(UserTypeRepository repo) {
		return args -> {
			if (repo.findByName("Trainee") == null) {
				UserType auszubildender = new UserType();
				auszubildender.setName("Trainee");
				repo.save(auszubildender);
			}

			if (repo.findByName("Trainer") == null) {
				UserType ausbilder = new UserType();
				ausbilder.setName("Trainer");
				repo.save(ausbilder);
			}

			if (repo.findByName("Student") == null) {
				UserType werkstudent = new UserType();
				werkstudent.setName("Student");
				repo.save(werkstudent);
			}
		};
	}
}
