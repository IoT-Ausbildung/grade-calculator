package com.example.gradecalculator.controller;

import com.example.gradecalculator.model.SubjectTO;
import com.example.gradecalculator.repository.SchoolYearRepository;
import com.example.gradecalculator.repository.SubjectRepository;
import com.example.gradecalculator.repository.UserRepository;
import com.example.gradecalculator.repository.UserSubjectRepository;
import com.example.gradecalculator.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class SubjectController {

    private final UserSubjectRepository userSubjectRepository;
    private final SchoolYearRepository schoolYearRepository;
    private final UserRepository userRepository;
    private final SubjectRepository subjectRepository;
    private final SubjectService subjectService;

    @Autowired
    public SubjectController(UserSubjectRepository userSubjectRepository,
                             SchoolYearRepository schoolYearRepository,
                             UserRepository userRepository,
                             SubjectRepository subjectRepository,
                             SubjectService subjectService) {
        this.userSubjectRepository = userSubjectRepository;
        this.schoolYearRepository = schoolYearRepository;
        this.userRepository = userRepository;
        this.subjectRepository = subjectRepository;
        this.subjectService = subjectService;
    }


    @GetMapping("/subjects")
    public String getSubjects(Model model) {
        List<SubjectTO> subjects = subjectService.getAllSubjects();
        model.addAttribute("subjects", subjects);
        return "subjectSelection";
    }



}