package com.example.gradecalculator.mapper;

import com.example.gradecalculator.repository.UserSubjectRepository;
import com.example.gradecalculator.service.SubjectSelectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubjectSelectionImp extends SubjectSelectionService {

    @Autowired
    private UserSubjectRepository userSubjectRepository;

    public SubjectSelectionImp(UserSubjectRepository userSubjectRepository) {
        super(userSubjectRepository);
    }

    @Override
    public void saveUserSubjects(String schoolYearName, Long userId, List<String> subjects) {

    }

    @Override
    public void getSelectedSubjectsForSchoolYear(String schoolYearName, Long userId) {
    }
}

