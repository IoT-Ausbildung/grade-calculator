package com.example.gradecalculator.mapper;

import com.example.gradecalculator.entities.Subject;
import com.example.gradecalculator.entities.UserSubject;
import com.example.gradecalculator.model.SubjectTO;
import com.example.gradecalculator.repository.SubjectRepository;
import com.example.gradecalculator.repository.UserRepository;
import com.example.gradecalculator.repository.UserSubjectRepository;
import com.example.gradecalculator.service.SubjectService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class SubjectServiceImpl implements SubjectService {

    private final UserSubjectRepository userSubjectRepository;
    private final SubjectRepository subjectRepository;
    @Getter
    private final UserRepository userRepository;
    private final Map<Integer, Set<String>> selectedSubjectsByYear;

    @Autowired
    public SubjectServiceImpl(UserSubjectRepository userSubjectRepository,
                              SubjectRepository subjectRepository,
                              UserRepository userRepository) {
        this.userSubjectRepository = userSubjectRepository;
        this.subjectRepository = subjectRepository;
        this.userRepository = userRepository;
        this.selectedSubjectsByYear = new HashMap<>();
    }

    @Override
    public SubjectTO dataTO(Subject subject) {
        if (subject == null) {
            return null;
        }
        return new SubjectTO(subject.getId(), subject.getName(), subject.getDescription());
    }

    @Override
    public List<SubjectTO> getAllSubjects() {
        return StreamSupport.stream(subjectRepository.findAll().spliterator(), false)
                .map(this::dataTO)
                .sorted()
                .collect(Collectors.toList());
    }

    @Override
    public Set<UserSubject> getUserSubjectsByYearAndUserId(int year, Long userId) {
        return userSubjectRepository.findBySchoolYearAndUserId(year, userId);
    }

    @Override
    public void selectSubjectForYear(int year, String subject) {
        Set<String> selectedSubjects = selectedSubjectsByYear.getOrDefault(year, new HashSet<>());
        selectedSubjects.add(subject);
        selectedSubjectsByYear.put(year, selectedSubjects);
    }

    @Override
    public Set<String> getSelectedSubjectsForYear(int year) {
        return selectedSubjectsByYear.getOrDefault(year, new HashSet<>());
    }

    @Override
    public void removeSubjectForYear(int year, String subject) {
        Set<String> selectedSubjects = selectedSubjectsByYear.get(year);
        if (selectedSubjects != null) {
            selectedSubjects.remove(subject);
            if (selectedSubjects.isEmpty()) {
                selectedSubjectsByYear.remove(year);
            }
        }
    }

    @Override
    public void saveUserSubjects() {
        saveUserSubjects(null, null, null);
    }

    @Override
    public void saveUserSubjects(String schoolYearName, Long userId, List<String> subjects) {
        //ToDo:implement saving user subjects
    }

    @Override
    public void getSelectedSubjectsForSchoolYear() {
        getSelectedSubjectsForSchoolYear(null, null);
    }

    @Override
    public void getSelectedSubjectsForSchoolYear(String schoolYearName, Long userId) {
        //ToDo: Implement retrieving the selected subjects for a school
    }

    @Override
    public Subject findSubjectById(Long id) {
        return null;
    }

    @Override
    public List<UserSubject> getUserSubjectsByUserId(Long userId) {
        return null;
    }


}
