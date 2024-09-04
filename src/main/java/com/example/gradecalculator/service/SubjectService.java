package com.example.gradecalculator.service;

import com.example.gradecalculator.entities.SchoolYear;
import com.example.gradecalculator.entities.Subject;
import com.example.gradecalculator.entities.User;
import com.example.gradecalculator.entities.UserSubject;
import com.example.gradecalculator.model.SubjectTO;
import com.example.gradecalculator.repository.SchoolYearRepository;
import com.example.gradecalculator.repository.SubjectRepository;
import com.example.gradecalculator.repository.UserRepository;
import com.example.gradecalculator.repository.UserSubjectRepository;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class SubjectService {

    private final UserSubjectRepository userSubjectRepository;
    private final SubjectRepository subjectRepository;
    @Getter
    private final UserRepository userRepository;
    private final Map<Integer, Set<String>> selectedSubjectsByYear;
    private final SchoolYearRepository schoolYearRepository;

    @Autowired
    public SubjectService(UserSubjectRepository userSubjectRepository,
                          SubjectRepository subjectRepository,
                          UserRepository userRepository,
                          SchoolYearRepository schoolYearRepository
                          ) {
        this.userSubjectRepository = userSubjectRepository;
        this.subjectRepository = subjectRepository;
        this.userRepository = userRepository;
        this.selectedSubjectsByYear = new HashMap<>();
        this.schoolYearRepository = schoolYearRepository;
    }

    public SubjectTO dataTO(Subject subject) {
        if (subject == null) {
            return null;
        }
        return new SubjectTO(subject.getId(), subject.getName(), subject.getDescription());
    }

    public List<SubjectTO> getAllSubjects() {
        return StreamSupport.stream(subjectRepository.findAll().spliterator(), false)
                .map(this::dataTO)
                .sorted()
                .collect(Collectors.toList());
    }

    public Set<UserSubject> getUserSubjectsByYearAndUserId(int year, Long userId) {
        return userSubjectRepository.findBySchoolYearAndUserId(year, userId);
    }

    public void selectSubjectForYear(int year, String subject) {
        Set<String> selectedSubjects = selectedSubjectsByYear.getOrDefault(year, new HashSet<>());
        selectedSubjects.add(subject);
        selectedSubjectsByYear.put(year, selectedSubjects);
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

    public void saveSubjectYearSelecteduser(long userId, long subjectId, long yearId) {
        SchoolYear selectedYear = schoolYearRepository.findById(yearId).orElseThrow(()
                -> new IllegalArgumentException("Year not found"));
        Subject selectedSubject = subjectRepository.findById(subjectId).orElseThrow(()
                -> new IllegalArgumentException("Subject not found"));
        User selectedUser = userRepository.findById(userId).orElseThrow(()
                -> new IllegalArgumentException("User not found"));

        this.selectSubjectForYear(selectedYear.getStartDate().getYear(), selectedSubject.getName());

        var userSubject = new UserSubject(selectedUser, selectedSubject, selectedYear);
        userSubjectRepository.save(userSubject);
    }

    public TreeMap<String, Set<String>> selectedSubject(long userId) {

        var userSubjects = userSubjectRepository.findByUserId(userId);
        TreeMap<String, Set<String>> subjectsByYear = new TreeMap<>();
        for (UserSubject userSubject : userSubjects) {
            String year = userSubject.getSchoolYear().getName();
            String subject = userSubject.getSubject().getName();
            subjectsByYear.computeIfAbsent(year, k -> new TreeSet<>()).add(subject);
        }

        return subjectsByYear;
    }

    public void saveUserSubjects() {
        saveUserSubjects(null, null, null);
    }

    public void saveUserSubjects(String schoolYearName, Long userId, List<String> subjects) {
        //ToDo:implement saving user subjects
    }

    public void getSelectedSubjectsForSchoolYear() {
        getSelectedSubjectsForSchoolYear(null, null);
    }

    public void getSelectedSubjectsForSchoolYear(String schoolYearName, Long userId) {
        //ToDo: Implement retrieving the selected subjects for a school
    }

    public Subject findSubjectById(Long id) {
        return null;
    }

    public List<UserSubject> getUserSubjectsByUserId(Long userId) {
        return null;
    }
}