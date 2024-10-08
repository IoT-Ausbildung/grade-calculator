package com.example.gradecalculator.service;

import com.example.gradecalculator.entities.SchoolYear;
import com.example.gradecalculator.entities.Subject;
import com.example.gradecalculator.entities.User;
import com.example.gradecalculator.entities.UserSubject;
import com.example.gradecalculator.mapper.SubjectMapper;
import com.example.gradecalculator.model.SubjectTO;
import com.example.gradecalculator.model.UserSubjectTO;
import com.example.gradecalculator.repository.SchoolYearRepository;
import com.example.gradecalculator.repository.SubjectRepository;
import com.example.gradecalculator.repository.UserRepository;
import com.example.gradecalculator.repository.UserSubjectRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class SubjectService {

    private final UserSubjectRepository userSubjectRepository;
    private final SubjectRepository subjectRepository;
    private final UserRepository userRepository;
    private final SchoolYearRepository schoolYearRepository;
    private final SubjectMapper subjectMapper;

    @Autowired
    public SubjectService(UserSubjectRepository userSubjectRepository, SubjectRepository subjectRepository,
                          UserRepository userRepository, SchoolYearRepository schoolYearRepository,
                          SubjectMapper subjectMapper) {
        this.userSubjectRepository = userSubjectRepository;
        this.subjectRepository = subjectRepository;
        this.userRepository = userRepository;
        this.schoolYearRepository = schoolYearRepository;
        this.subjectMapper = subjectMapper;
    }

    public List<SubjectTO> getAllSubjects() {
        return StreamSupport.stream(subjectRepository.findAll().spliterator(), false)
                .map(subjectMapper::subjectToSubjectTO)
                .filter(Objects::nonNull)
                .sorted(Comparator.comparing(SubjectTO::getName))
                .collect(Collectors.toList());
    }

    public Set<UserSubject> getUserSubjectsByYearAndUserId(int year, Long userId) {
        return userSubjectRepository.findBySchoolYearAndUserId(year, userId);
    }

    public List<String> saveSubjects(String[] selectedValues, long userId) {

        var errors = new ArrayList<String>();
        User selectedUser = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));
        for (String entry : selectedValues) {
            var splittedString = entry.split("-");
            long selectedSubjectId = Long.parseLong(splittedString[0]);
            long selectedYearId = Long.parseLong(splittedString[1]);
            String error = saveSubjectYearSelectedUser(selectedUser, selectedSubjectId, selectedYearId);
            if (error != null) {
                errors.add(error);
            }
        }

        return errors;
    }

    public String saveSubjectYearSelectedUser(long userID, long subjectId, long yearId) {
        var user = userRepository.findById(userID).orElseThrow(() -> new IllegalArgumentException("User not found"));
        return saveSubjectYearSelectedUser(user, subjectId, yearId);
    }

    private String saveSubjectYearSelectedUser(User selectedUser, long subjectId, long yearId) {

        Subject selectedSubject = subjectRepository.findById(subjectId).orElseThrow(() -> new IllegalArgumentException("Subject not found"));
        SchoolYear selectedYear = schoolYearRepository.findById(yearId).orElseThrow(() -> new IllegalArgumentException("Year not found"));
        var subjects = userSubjectRepository.findByUserId(selectedUser.getId());
        boolean exists = subjects.stream().anyMatch(item -> item.getSubject().getId().equals(subjectId) && item.getSchoolYear().getId().equals(yearId));
        if (!exists) {
            UserSubject userSubject = new UserSubject();
            userSubject.setUser(selectedUser);
            userSubject.setSubject(selectedSubject);
            userSubject.setSchoolYear(selectedYear);
            userSubjectRepository.save(userSubject);
            return null;
        }
        return ("Subject already selected for the given year: " + selectedSubject.getName() + " - " + selectedYear.getName());
    }

    public TreeMap<String, Set<UserSubjectTO>> selectedSubject(long userId) {
        var userSubjects = userSubjectRepository.findByUserId(userId);
        TreeMap<String, Set<UserSubjectTO>> subjectsByYear = new TreeMap<>();

        for (UserSubject userSubject : userSubjects) {
            String year = userSubject.getSchoolYear().getName();
            var userSubjectTO = new UserSubjectTO();
            userSubjectTO.setID(userSubject.getId());
            userSubjectTO.setName(userSubject.getSubject().getName());
            subjectsByYear.computeIfAbsent(year, k -> new TreeSet<>(Comparator.comparing(UserSubjectTO::getName))).add(userSubjectTO);
        }

        return subjectsByYear;
    }

    @Transactional
    public boolean deleteSubject(Long subjectId, String userId) {
        Optional<UserSubject> userSubjectOpt = userSubjectRepository.findById(subjectId);

        if (!userSubjectOpt.isPresent()) {
            System.out.println("User subject not found with ID: " + subjectId);
            return false;
        }
        UserSubject userSubject = userSubjectOpt.get();
        if (!userSubject.getUser().getId().equals(Long.parseLong(userId))) {
            System.out.println("User ID does not match for subject ID: " + subjectId);
            return false;
        }
        userSubjectRepository.delete(userSubject);
        System.out.println("Subject with ID " + subjectId + " successfully deleted.");
        return true;
    }
}