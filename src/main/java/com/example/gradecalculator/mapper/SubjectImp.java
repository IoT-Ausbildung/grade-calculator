package com.example.gradecalculator.mapper;

import com.example.gradecalculator.entities.Subject;
import com.example.gradecalculator.entities.UserSubject;
import com.example.gradecalculator.model.SubjectTO;
import com.example.gradecalculator.repository.UserSubjectRepository;
import com.example.gradecalculator.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class SubjectImp extends SubjectService {

    @Override
    public SubjectTO dataTO(Subject subject) {
        if ( subject == null ) {
            return null;
        }

        SubjectTO subjectTO = new SubjectTO();

        return subjectTO;
    }

    @Autowired
    private UserSubjectRepository userSubjectRepository;

    @Override
    public Set<UserSubject> getUserSubjectsByYearAndUserId(int year, Long userId) {
        return super.getUserSubjectsByYearAndUserId(year, userId);
    }

    public SubjectImp(UserSubjectRepository userSubjectRepository) {
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

