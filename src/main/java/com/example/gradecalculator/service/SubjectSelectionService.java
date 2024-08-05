package com.example.gradecalculator.service;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Service
public abstract class SubjectSelectionService {

    private Map<Integer, Set<String>> selectedSubjectsByYear;

    public SubjectSelectionService() {
        selectedSubjectsByYear = new HashMap<>();
    }

    public void selectSubjectForYear(int year, String subject) {
        Set<String> selectedSubjects = selectedSubjectsByYear.getOrDefault(year, new HashSet<>());
        selectedSubjects.add(subject);
        selectedSubjectsByYear.put(year, selectedSubjects);
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
}

