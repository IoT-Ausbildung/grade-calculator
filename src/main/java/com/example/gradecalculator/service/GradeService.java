package com.example.gradecalculator.service;

import com.example.gradecalculator.entities.GradeType;
import com.example.gradecalculator.entities.User;
import com.example.gradecalculator.entities.UserGrade;
import com.example.gradecalculator.entities.UserSubject;
import com.example.gradecalculator.model.GradeTO;
import com.example.gradecalculator.repository.GradeTypeRepository;
import com.example.gradecalculator.repository.UserGradeRepository;
import com.example.gradecalculator.repository.UserRepository;
import com.example.gradecalculator.repository.UserSubjectRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class GradeService {
    private final UserGradeRepository userGradeRepository;
    private final UserSubjectRepository userSubjectRepository;
    private final UserRepository userRepository;
    private final GradeTypeRepository gradeTypeRepository;

    public GradeService(UserGradeRepository userGradeRepository, UserSubjectRepository userSubjectRepository, UserRepository userRepository, GradeTypeRepository gradeTypeRepository) {
        this.userGradeRepository = userGradeRepository;
        this.userSubjectRepository = userSubjectRepository;
        this.userRepository = userRepository;
        this.gradeTypeRepository = gradeTypeRepository;
    }

    public List<UserGrade> getSelectedGrades(Long userId) {
        return userGradeRepository.findByUserId(userId);
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
}
