package com.example.gradecalculator.mapper;

import com.example.gradecalculator.entities.Subject;
import com.example.gradecalculator.entities.UserSubject;
import com.example.gradecalculator.model.SubjectTO;
import com.example.gradecalculator.repository.SubjectRepository;
import com.example.gradecalculator.repository.UserRepository;
import com.example.gradecalculator.repository.UserSubjectRepository;
import com.example.gradecalculator.service.SubjectService;
import jakarta.transaction.Transactional;
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
        return null;
    }


    @Override
    public void selectSubjectForYear(int year, String subject) {
        Set<String> selectedSubjects = selectedSubjectsByYear.getOrDefault(year, new HashSet<>());
        selectedSubjects.add(subject);
        selectedSubjectsByYear.put(year, selectedSubjects);
    }

    @Override
    public Set<String> getSelectedSubjectsForYear(int year) {
        return null;
    }

    @Override
    public void removeSubjectForYear(int year, String subject) {

    }

    @Override
    public void saveUserSubjects() {

    }


    @Override
    public void saveUserSubjects(String schoolYearName, Long userId, List<String> subjects) {
        //ToDo:implement saving user subjects
    }

    @Override
    public void getSelectedSubjectsForSchoolYear() {

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
    @Override
    @Transactional
    public boolean deleteSubject(Long subjectId, String userId) {
        Optional<UserSubject> userSubjectOpt = userSubjectRepository.findById(subjectId);

        if (userSubjectOpt.isPresent()) {
            UserSubject userSubject = userSubjectOpt.get();
            if (userSubject.getUser().getId().equals(Long.parseLong(userId))) {
                userSubjectRepository.delete(userSubject);
                boolean exists = userSubjectRepository.existsById(subjectId);
                if (!exists) {
                    System.out.println("Subject with ID " + subjectId + " successfully deleted.");
                    return true;
                } else {
                    System.out.println("Failed to delete the subject with ID " + subjectId);
                }
            } else {
                System.out.println("User ID does not match for subject ID: " + subjectId);
            }
        } else {
            System.out.println("User subject not found with ID: " + subjectId);
        }

        return false;
    }
}
