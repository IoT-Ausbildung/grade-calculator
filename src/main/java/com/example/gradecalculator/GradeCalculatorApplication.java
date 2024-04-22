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
				saveUsertyps("Trainee", repo);
			}
			if (repo.findByName("Trainer") == null) {
				saveUsertyps("Trainer", repo);
			}
			if (repo.findByName("Student") == null) {
				saveUsertyps("Student", repo);
			}
		};
	}
	public void saveUsertyps(String typs, UserTypeRepository repo) {
		UserType usertype  = new UserType();
		usertype.setName(typs);
		repo.save(usertype);
	}
}
