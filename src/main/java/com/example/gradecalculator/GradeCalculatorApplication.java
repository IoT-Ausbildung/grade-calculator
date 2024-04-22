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

		var traineeName = "Trainee";
		var trainerName = "Trainer";
		var studentName = "Student";

		return args -> {
			if (repo.findByName(traineeName) == null) {
				saveUsertyps(traineeName, repo);
			}
			if (repo.findByName(traineeName) == null) {
				saveUsertyps(trainerName, repo);
			}
			if (repo.findByName(studentName) == null) {
				saveUsertyps(studentName, repo);
			}
		};
	}
	public void saveUsertyps(String typs, UserTypeRepository repo) {
		UserType usertype  = new UserType();
		usertype.setName(typs);
		repo.save(usertype);
	}
}