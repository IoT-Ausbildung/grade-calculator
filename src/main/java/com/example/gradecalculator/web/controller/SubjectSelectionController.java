package com.example.gradecalculator.web.controller;

import com.example.gradecalculator.service.SubjectSelectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Set;

@Controller
@RequestMapping("/subjectselection")
public class SubjectSelectionController {

    @Autowired
    private SubjectSelectionService subjectSelectionService;

    @GetMapping("/selected")
    public String showSelectedSubjects(@RequestParam("year") int year, @RequestParam("userId") Long userId, Model model) {
        Set<String> selectedSubjects = subjectSelectionService.getSelectedSubjectsForYear(year, userId);
        model.addAttribute("selectedSubjects", selectedSubjects);
        model.addAttribute("year", year);
        model.addAttribute("userId", userId);
        return "selectedSubjects";
    }
}


