package com.example.gradecalculator.controller;

import com.example.gradecalculator.model.SubjectTO;
import com.example.gradecalculator.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class SubjectController {
    private final SubjectService subjectService;

    @Autowired
    public SubjectController(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @GetMapping("/subjects")
    public String getSubjects(Model model) {
        List<SubjectTO> subjects = subjectService.getAllSubjects();
        model.addAttribute("subjects", subjects);
        return "subjectSelection";
    }
}