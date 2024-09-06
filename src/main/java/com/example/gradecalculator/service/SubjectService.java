package com.example.gradecalculator.service;

import com.example.gradecalculator.entities.Subject;
import com.example.gradecalculator.entities.UserSubject;
import com.example.gradecalculator.model.SubjectTO;

import java.util.List;
import java.util.Set;

public interface SubjectService {

    SubjectTO dataTO(Subject subject);

    List<SubjectTO> getAllSubjects();

    Set<UserSubject> getUserSubjectsByYearAndUserId(int year, Long userId);

    void selectSubjectForYear(int year, String subject);

    Set<String> getSelectedSubjectsForYear(int year);

    void removeSubjectForYear(int year, String subject);

    void saveUserSubjects();

    void saveUserSubjects(String schoolYearName, Long userId, List<String> subjects);

    void getSelectedSubjectsForSchoolYear();

    void getSelectedSubjectsForSchoolYear(String schoolYearName, Long userId);

    Subject findSubjectById(Long id);

    List<UserSubject> getUserSubjectsByUserId(Long userId);

    boolean deleteSubject(Long subjectId, String userId);
}



