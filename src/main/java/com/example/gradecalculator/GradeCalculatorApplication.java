package com.example.gradecalculator;

import com.example.gradecalculator.entities.GradeSystem;
import com.example.gradecalculator.entities.SchoolYear;
import com.example.gradecalculator.entities.Subject;
import com.example.gradecalculator.entities.UserType;
import com.example.gradecalculator.enums.Subjects;
import com.example.gradecalculator.enums.UserNames;
import com.example.gradecalculator.repository.GradeSystemRepository;
import com.example.gradecalculator.repository.SchoolYearRepository;
import com.example.gradecalculator.repository.SubjectRepository;
import com.example.gradecalculator.repository.UserTypeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;

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
        UserType usertype = new UserType();
        usertype.setName(types);
        repo.save(usertype);
    }

    @Bean
    public CommandLineRunner demoData2(SubjectRepository repo) {
        return args -> {
            Subjects[] subjectsToCheck = {Subjects.ITT1, Subjects.ITT2, Subjects.ITS, Subjects.AP, Subjects.BGP, Subjects.Englisch, Subjects.Deutsch, Subjects.Sport, Subjects.PuG};

            for (Subjects subject : subjectsToCheck) {
                switch (subject) {
                    case ITT1, ITT2, ITS, AP, BGP, Englisch, Deutsch, Sport, PuG:
                        if (repo.findByName(subject.getValue()) == null) {
                            saveUserSubjects(subject.getValue(), repo);
                        }
                        break;
                    default:
                        break;
                }
            }
        };
    }

    public void saveUserSubjects(String subjects, SubjectRepository repo) {
        Subject subject = new Subject();
        subject.setName(subjects);
        repo.save(subject);
    }

    @Bean
    public CommandLineRunner demoData3(SchoolYearRepository schoolYearRepository, GradeSystemRepository gradeSystemRepository) {

        return args ->{
            if (schoolYearRepository.count() == 0) {
                GradeSystem gradeSystem = new GradeSystem();
                gradeSystemRepository.save(gradeSystem);
                SchoolYear year1 = new SchoolYear("2023-2024", LocalDate.of(2023, 9, 1), LocalDate.of(2024, 9, 30), gradeSystem);
                SchoolYear year2 = new SchoolYear("2024-2025", LocalDate.of(2024, 9, 1), LocalDate.of(2025, 9, 30), gradeSystem);
                SchoolYear year3 = new SchoolYear("2025-2026", LocalDate.of(2025, 9, 1), LocalDate.of(2026, 9, 30), gradeSystem);
                SchoolYear year4 = new SchoolYear("2026-2027", LocalDate.of(2026, 9, 1), LocalDate.of(2027, 9, 30), gradeSystem);
                SchoolYear year5 = new SchoolYear("2027-2028", LocalDate.of(2027, 9, 1), LocalDate.of(2028, 9, 30), gradeSystem);

                schoolYearRepository.save(year1);
                schoolYearRepository.save(year2);
                schoolYearRepository.save(year3);
                schoolYearRepository.save(year4);
                schoolYearRepository.save(year5);

            }
        };

    }
}