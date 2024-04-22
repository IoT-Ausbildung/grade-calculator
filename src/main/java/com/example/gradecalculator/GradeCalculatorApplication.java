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

		var traineeName = UserNames.TRAINEE;
		var trainerName = UserNames.TRAINER;
		var studentName = UserNames.STUDENT;

		return args -> {
			if (repo.findByName(UserNames.TRAINEE) == null) {
				saveUsertypes(UserNames.TRAINEE, repo);
			}
			if (repo.findByName(traineeName) == null) {
				saveUsertypes(trainerName, repo);
			}
			if (repo.findByName(studentName) == null) {
				saveUsertypes(studentName, repo);
			}
		};
	}
	public void saveUsertypes(UserNames types, UserTypeRepository repo) {
		UserType usertype  = new UserType();
		usertype.setName(types);
		repo.save(usertype);
	}
}