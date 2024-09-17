package com.example.gradecalculator.config;

import com.example.gradecalculator.entities.SchoolYear;
import com.example.gradecalculator.entities.Subject;
import com.example.gradecalculator.entities.UserType;
import com.example.gradecalculator.enums.UserNames;
import com.example.gradecalculator.repository.SchoolYearRepository;
import com.example.gradecalculator.repository.SubjectRepository;
import com.example.gradecalculator.repository.UserTypeRepository;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.List;

@Configuration
public class DBInitializer {
    private final UserTypeRepository userTypeRepository;
    private final SubjectRepository subjectRepository;
    private final SchoolYearRepository schoolYearRepository;

    public DBInitializer(UserTypeRepository userTypeRepository,
                         SubjectRepository subjectRepository,
                         SchoolYearRepository schoolYearRepository) {
        this.userTypeRepository = userTypeRepository;
        this.subjectRepository = subjectRepository;
        this.schoolYearRepository = schoolYearRepository;
    }

    public void seedData() {
        seedUserTypes();
        seedSubjects();
        seedSchoolYears();
    }

    private void seedSchoolYears() {

        if (schoolYearRepository.count() == 0) {
            String[] schoolYearData = {
                    "2023/24", "2024/25", "2025/26", "2026/27", "2027/28"
            };
            LocalDate startDate = LocalDate.of(2023, 9, 1);
            LocalDate endDate = startDate.plusYears(1).minusDays(1);

            for (String yearData : schoolYearData) {
                SchoolYear schoolYear = new SchoolYear(yearData, startDate, endDate);
                schoolYearRepository.save(schoolYear);
                startDate = startDate.plusYears(1);
                endDate = endDate.plusYears(1);
            }
        }
    }

    private void seedUserTypes() {

        if (userTypeRepository.findByName(UserNames.TRAINEE.getValue()) == null) {
            saveUserTypes(UserNames.TRAINEE.getValue());
        }
        if (userTypeRepository.findByName(UserNames.TRAINER.getValue()) == null) {
            saveUserTypes(UserNames.TRAINER.getValue());
        }
        if (userTypeRepository.findByName(UserNames.STUDENT.getValue()) == null) {
            saveUserTypes(UserNames.STUDENT.getValue());
        }
    }

    private void saveUserTypes(String types) {
        UserType usertype = new UserType();
        usertype.setName(types);
        userTypeRepository.save(usertype);
    }

    private void seedSubjects() {
        List<Subject> subjects = List.of(
                new Subject("ITT1", "IT Technic 1 (Subject about hardware components of devices in combination with economic topics and processes in a company)"),
                new Subject("ITT2", "IT Technic 2 (Technical subject concentrated on security)"),
                new Subject("ITS", "IT Systems (Subject covers wide range of networking topics)"),
                new Subject("AP", "Application development and programming (Learning Java and Database)"),
                new Subject("BGP", "Business and economic processes in general"),
                new Subject("English", "English language"),
                new Subject("German", "German language"),
                new Subject("Sport", "Physical activities"),
                new Subject("PuG", "Politics and Society (Political and social topics of Germany, such as elections, education/health care system etc)")
        );

        for (Subject subject : subjects) {
            var existingEntry = subjectRepository.findByName(subject.getName());
            if (existingEntry.isEmpty()) {
                subjectRepository.save(subject);
            } else {
                var dbSubject = existingEntry.get();
                dbSubject.setDescription(subject.getDescription());
                subjectRepository.save(dbSubject);
            }
        }
    }
}
