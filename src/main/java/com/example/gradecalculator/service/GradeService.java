package com.example.gradecalculator.service;

import com.example.gradecalculator.entities.GradeType;
import com.example.gradecalculator.entities.User;
import com.example.gradecalculator.entities.UserGrade;
import com.example.gradecalculator.entities.UserSubject;
import com.example.gradecalculator.mapper.GradeMapper;
import com.example.gradecalculator.model.GradeTO;
import com.example.gradecalculator.repository.GradeTypeRepository;
import com.example.gradecalculator.repository.UserGradeRepository;
import com.example.gradecalculator.repository.UserRepository;
import com.example.gradecalculator.repository.UserSubjectRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    public List<GradeTO> getSelectedGrades(Long userId) {
        List<UserGrade> userGrades = userGradeRepository.findByUserId(userId);
        return userGrades.stream()
                .map(gradeMapper::userGradeToGradeTO)
                .collect(Collectors.toList());
    }

    public Map<Long, Map<String, List<Integer>>> groupGradesBySubjectAndType(Long userId) {
        List<GradeTO> selectedGrades = getSelectedGrades(userId);
        return selectedGrades.stream()
                .collect(Collectors.groupingBy(
                        GradeTO::getUserSubjectId,
                        Collectors.groupingBy(
                                GradeTO::getGradeTypeName,
                                Collectors.mapping(GradeTO::getGradeValue, Collectors.toList())
                        )
                ));
    }
}
