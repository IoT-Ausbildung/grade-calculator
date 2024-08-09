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

    public DBInitializer(UserTypeRepository userTypeRepository, SubjectRepository subjectRepository, SchoolYearRepository schoolYearRepository) {
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
            String[]  schoolYearData= {
                    "2023-2024", "2024.2025", "2025-2026", "2026-2027", "2027-2028"
            };
            LocalDate startDate = LocalDate.of(2023, 9,1);
            LocalDate endDate = startDate.plusYears(1).minusDays(1);

            for(String yearData : schoolYearData) {
                SchoolYear schoolYear = new SchoolYear(yearData,startDate,endDate);
                schoolYearRepository.save(schoolYear);
                startDate = startDate.plusYears(1);
                endDate = endDate.plusYears(1);
            }
        }
    }

    private void seedUserTypes() {

            if (userTypeRepository.findByName(UserNames.TRAINEE.getValue()) == null) {
                saveUsertypes(UserNames.TRAINEE.getValue());
            }
            if (userTypeRepository.findByName(UserNames.TRAINER.getValue()) == null) {
                saveUsertypes(UserNames.TRAINER.getValue());
            }
            if (userTypeRepository.findByName(UserNames.STUDENT.getValue()) == null) {
                saveUsertypes(UserNames.STUDENT.getValue());
            }
        };

    private void saveUsertypes(String types) {
        UserType usertype = new UserType();
        usertype.setName(types);
        userTypeRepository.save(usertype);
    }

    private void seedSubjects() {
        List<String> subjectNames = List.of(
                "ITT1", "ITT2", "ITS", "AP", "BGP", "Englisch", "Deutsch", "Sport", "PuG"
        );

        for (String subjectName : subjectNames) {
            if (subjectRepository.findByName(subjectName) == null) {
                Subject subject = new Subject();
                subject.setName(subjectName);
                subject.setDescription(subjectName + " Description");
                subjectRepository.save(subject);
            }
        }}}
