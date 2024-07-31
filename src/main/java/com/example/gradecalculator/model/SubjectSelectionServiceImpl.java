package com.example.gradecalculator.model;

import com.example.gradecalculator.entities.UserSubject;
import com.example.gradecalculator.repository.UserSubjectRepository;
import com.example.gradecalculator.service.SubjectSelectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SubjectSelectionServiceImpl extends SubjectSelectionService {

    @Autowired
    private UserSubjectRepository userSubjectRepository;

    @Override
    public Set<String> getSelectedSubjectsForYear(int year, Long userId) {
        Set<UserSubject> userSubjects = userSubjectRepository.findBySchoolYearAndUserId(year, userId);
        return userSubjects.stream()
                .map(userSubject -> userSubject.getSubject().getName())
                .collect(Collectors.toSet());
    }
}
