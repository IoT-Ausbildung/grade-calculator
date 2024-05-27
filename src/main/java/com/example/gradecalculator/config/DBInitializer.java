package com.example.gradecalculator.config;

import com.example.gradecalculator.entities.SchoolYear;
import com.example.gradecalculator.entities.Subject;
import com.example.gradecalculator.entities.UserType;
import com.example.gradecalculator.enums.Subjects;
import com.example.gradecalculator.enums.UserNames;
import com.example.gradecalculator.repository.SchoolYearRepository;
import com.example.gradecalculator.repository.SubjectRepository;
import com.example.gradecalculator.repository.UserTypeRepository;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;

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

            Subjects[] subjectsToCheck = {Subjects.ITT1, Subjects.ITT2, Subjects.ITS, Subjects.AP, Subjects.BGP, Subjects.Englisch, Subjects.Deutsch, Subjects.Sport, Subjects.PuG};

            for (Subjects subject : subjectsToCheck) {
                switch (subject) {
                    case ITT1, ITT2, ITS, AP, BGP, Englisch, Deutsch, Sport, PuG:
                        if (subjectRepository.findByName(subject.getValue()) == null) {
                            saveUserSubjects(subject.getValue());
                        }
                        break;
                    default:
                        break;
                }
            }
        }

    private void saveUserSubjects(String subjects) {
        Subject subject = new Subject();
        subject.setName(subjects);
        subjectRepository.save(subject);
    }
}

