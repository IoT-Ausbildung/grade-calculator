package com.example.gradecalculator.controller;

import com.example.gradecalculator.repository.GradeTypeRepository;
import com.example.gradecalculator.repository.UserGradeRepository;
import com.example.gradecalculator.repository.UserSubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/grades")
public class GradesController {

    @Autowired
    private UserSubjectRepository userSubjectRepository;

    @Autowired
    private GradeTypeRepository gradeTypeRepository;

    @Autowired
    private UserGradeRepository userGradeRepository;

    public GradesController(UserSubjectRepository userSubjectRepository, GradeTypeRepository gradeTypeRepository, UserGradeRepository userGradeRepository) {
        this.userSubjectRepository = userSubjectRepository;
        this.gradeTypeRepository = gradeTypeRepository;
        this.userGradeRepository = userGradeRepository;
    }
}


