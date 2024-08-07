package com.example.gradecalculator.service;

import com.example.gradecalculator.entities.UserSubject;
import com.example.gradecalculator.repository.UserSubjectRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public abstract class SubjectSelectionService {



    private final UserSubjectRepository userSubjectRepository;

    public Set<UserSubject> getUserSubjectsByYearAndUserId(int year, Long userId) {
        return userSubjectRepository.findBySchoolYearAndUserId(year, userId);
    }

    private Map<Integer, Set<String>> selectedSubjectsByYear;

    public SubjectSelectionService(UserSubjectRepository userSubjectRepository) {
        this.userSubjectRepository = userSubjectRepository;
        selectedSubjectsByYear = new HashMap<>();
    }

    public void selectSubjectForYear(int year, String subject) {
        Set<String> selectedSubjects = selectedSubjectsByYear.getOrDefault(year, new HashSet<>());
        selectedSubjects.add(subject);
        selectedSubjectsByYear.put(year, selectedSubjects);
    }

    public boolean checkIfSubjectExists(String subjectName) {
        return userSubjectRepository.existsBySubjectName(subjectName);
    }

    public boolean isSubjectSelectedForYear(int year, String subject) {
        Set<String> selectedSubjects = selectedSubjectsByYear.get(year);
        return selectedSubjects != null && selectedSubjects.contains(subject);
    }

    public Set<String> getSelectedSubjectsForYear(int year) {
        return selectedSubjectsByYear.getOrDefault(year, new HashSet<>());
    }

    public void removeSubjectForYear(int year, String subject) {
            Set<String> selectedSubjects = selectedSubjectsByYear.get(year);
            if (selectedSubjects != null) {
                selectedSubjects.remove(subject);
                if (selectedSubjects.isEmpty()) {
                    selectedSubjectsByYear.remove(year);
                }
            }
        }

    public abstract void saveUserSubjects(String schoolYearName, Long userId, List<String> subjects);

    public abstract void getSelectedSubjectsForSchoolYear(String schoolYearName, Long userId);
}

