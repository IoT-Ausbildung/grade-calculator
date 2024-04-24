package com.example.gradecalculator;

import com.example.gradecalculator.entities.UserType;
import com.example.gradecalculator.repository.UserTypeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import com.example.gradecalculator.enums.UserNames;

@SpringBootApplication
public class GradeCalculatorApplication {

	public static void main(String[] args) {
		SpringApplication.run(GradeCalculatorApplication.class, args);
	}

	@Bean
	public CommandLineRunner demoData(UserTypeRepository repo) {

		return args -> {
			if (repo.findByName(UserNames.TRAINEE.getValue()) == null) {
				saveUsertypes(UserNames.TRAINEE.getValue(), repo);
			}
			if (repo.findByName(UserNames.TRAINER.getValue()) == null) {
				saveUsertypes(UserNames.TRAINER.getValue(), repo);
			}
			if (repo.findByName(UserNames.STUDENT.getValue()) == null) {
				saveUsertypes(UserNames.STUDENT.getValue(), repo);
			}
		};
	}

	public void saveUsertypes(String types, UserTypeRepository repo) {
		UserType usertype  = new UserType();
		usertype.setName(types);
		repo.save(usertype);
	}
}