package com.example.gradecalculator.mapper;

import com.example.gradecalculator.entities.UserSubject;
import com.example.gradecalculator.repository.UserSubjectRepository;
import com.example.gradecalculator.service.SubjectSelectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class SubjectSelectionImp extends SubjectSelectionService {

    @Autowired
    private UserSubjectRepository userSubjectRepository;

    @Override
    public Set<UserSubject> getUserSubjectsByYearAndUserId(int year, Long userId) {
        return super.getUserSubjectsByYearAndUserId(year, userId);
    }

    public SubjectSelectionImp(UserSubjectRepository userSubjectRepository) {
        super(userSubjectRepository);
    }

    @Override
    public void removeSubjectForYear(int year, String subject) {
        super.removeSubjectForYear(year, subject);
    }

    @Override
    public void saveUserSubjects() {
        super.saveUserSubjects();
    }

    @Override
    public void saveUserSubjects(String schoolYearName, Long userId, List<String> subjects) {

    }

    @Override
    public void getSelectedSubjectsForSchoolYear() {
        super.getSelectedSubjectsForSchoolYear();
    }

    @Override
    public void getSelectedSubjectsForSchoolYear(String schoolYearName, Long userId) {
    }
}

