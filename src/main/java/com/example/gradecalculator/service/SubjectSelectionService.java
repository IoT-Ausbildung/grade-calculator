package com.example.gradecalculator.service;

import com.example.gradecalculator.enums.Subjects;
import com.example.gradecalculator.web.model.SubjectTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

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

    public abstract Set<String> getSelectedSubjectsForYear(int year, Long userId);
    @Autowired
    private List<SubjectTO> subjectTOs;

    public List<Subjects> getSelectedSubjects() {
        return subjectTOs.stream()
                .filter(SubjectTO::isSelected)
                .map(SubjectTO::getSubject)
                .collect(Collectors.toList()).reversed();
    }

    public void updateSelectedSubjects(List<Subjects> selectedSubjects) {
        for (SubjectTO subjectTO : subjectTOs) {
            subjectTO.setSelected(selectedSubjects.contains(subjectTO.getSubject()));
        }
    }
}

