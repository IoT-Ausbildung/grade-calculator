package com.example.gradecalculator.service;

import com.example.gradecalculator.entities.GradeType;
import com.example.gradecalculator.entities.User;
import com.example.gradecalculator.entities.UserGrade;
import com.example.gradecalculator.entities.UserSubject;
import com.example.gradecalculator.enums.GradeTypes;
import com.example.gradecalculator.mapper.GradeMapper;
import com.example.gradecalculator.model.GradeTO;
import com.example.gradecalculator.repository.GradeTypeRepository;
import com.example.gradecalculator.repository.UserGradeRepository;
import com.example.gradecalculator.repository.UserRepository;
import com.example.gradecalculator.repository.UserSubjectRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.StreamSupport.stream;

@Service
public class GradeService {
    private final UserGradeRepository userGradeRepository;
    private final UserSubjectRepository userSubjectRepository;
    private final UserRepository userRepository;
    private final GradeTypeRepository gradeTypeRepository;
    private final GradeMapper gradeMapper;


    public GradeService(UserGradeRepository userGradeRepository, UserSubjectRepository userSubjectRepository, UserRepository userRepository, GradeTypeRepository gradeTypeRepository, GradeMapper gradeMapper) {
        this.userGradeRepository = userGradeRepository;
        this.userSubjectRepository = userSubjectRepository;
        this.userRepository = userRepository;
        this.gradeTypeRepository = gradeTypeRepository;
        this.gradeMapper = gradeMapper;
    }

    @Transactional
    public void saveGrade(GradeTO gradeTO, Long userId) {
        UserSubject userSubject = userSubjectRepository.findById(gradeTO.getUserSubjectId())
                .orElseThrow(() -> new RuntimeException("User subject not found"));

        GradeType gradeType = gradeTypeRepository.findById(gradeTO.getGradeTypeId())
                .orElseThrow(() -> new RuntimeException("Grade type not found"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));


        UserGrade grade = new UserGrade();
        grade.setUserSubject(userSubject);
        grade.setGradeType(gradeType);
        grade.setGrade(gradeTO.getGradeValue());
        grade.setUser(user);
        userGradeRepository.save(grade);
    }

    public Map<GradeTypes, List<GradeTO>> getAllUserGrades(long userId) {
        return stream(userGradeRepository.findByUserId(userId).spliterator(), false)
                .map(gradeMapper::userGradeToGradeTO)
                .filter(Objects::nonNull)
                .sorted(Comparator.comparing(GradeTO::getGradeTypeName))
                .collect(Collectors.groupingBy(gradeTO -> mapToGradeTypesEnum(gradeTO.getGradeTypeName())));
    }

    private GradeTypes mapToGradeTypesEnum(String gradeTypeName) {
        return Arrays.stream(GradeTypes.values())
                .filter(gradeType -> gradeType.getValue().equalsIgnoreCase(gradeTypeName))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown grade type: " + gradeTypeName));
    }
}
