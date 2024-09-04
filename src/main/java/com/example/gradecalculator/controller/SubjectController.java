package com.example.gradecalculator.controller;

import com.example.gradecalculator.entities.SchoolYear;
import com.example.gradecalculator.entities.Subject;
import com.example.gradecalculator.entities.UserSubject;
import com.example.gradecalculator.mapper.SubjectMapper;
import com.example.gradecalculator.model.SubjectTO;
import com.example.gradecalculator.repository.SchoolYearRepository;
import com.example.gradecalculator.repository.SubjectRepository;
import com.example.gradecalculator.repository.UserRepository;
import com.example.gradecalculator.repository.UserSubjectRepository;
import com.example.gradecalculator.service.SubjectService;
import com.example.gradecalculator.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;

@Controller
public class SubjectController {
    private final SubjectService subjectService;
    private final SubjectRepository subjectRepository;
    private final UserSubjectRepository userSubjectRepository;
    private final SchoolYearRepository schoolYearRepository;
    private final SubjectMapper subjectMapper;
    private final UserService userService;
    private final UserRepository userRepository;

    @Autowired
    public SubjectController(SubjectService subjectService, UserSubjectRepository userSubjectRepository,
                             SubjectRepository subjectRepository, SchoolYearRepository schoolYearRepository, SubjectMapper subjectMapper, UserService userService, UserRepository userRepository) {
        this.userSubjectRepository = userSubjectRepository;
        this.subjectRepository = subjectRepository;
        this.schoolYearRepository = schoolYearRepository;
        this.subjectService = subjectService;
        this.subjectMapper = subjectMapper;
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @GetMapping("/subjects")
    public String getSubjects(Model model) {
        List<SubjectTO> subjects = subjectService.getAllSubjects();
        model.addAttribute("subjects", subjects);
        return "subjectSelection";
    }

    @GetMapping("/userSubject/form")
    public String showUserSubjectForm(Model model) {
        List<SchoolYear> years = schoolYearRepository.findAll();
        List<Subject> subjects = (List<Subject>) subjectRepository.findAll();
        model.addAttribute("years", years);
        model.addAttribute("subjects", subjects);
        model.addAttribute("selectedSubjects", new ArrayList<UserSubject>());

        return "subjectSelection";
    }

    @PostMapping("/userSubject/save")
    public String saveUserSubject(@RequestParam("schoolYear")long selectedYear,
                                  @RequestParam("subjects")long selectedSubject,
                                  Authentication authentication, Model model) {
        try {
            var userID = userService.getAuthenticatedUserId(authentication);
            subjectService.saveSubjectYearSelecteduser(userID, selectedSubject, selectedYear);
            return "redirect:/userSubject/selected";

        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "error";
        }
    }

    @GetMapping("userSubject/selected")
    public String showSelectedSubjects(Model model, Authentication authentication) {
        try {
            var userId = userService.getAuthenticatedUserId(authentication);
            TreeMap<String, Set<String>> subjectsByYear = subjectService.selectedSubject(userId);
            model.addAttribute("subjectsByYear", subjectsByYear);
            model.addAttribute("user", userId);

            return "userSubjects";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());

            return "error";
        }

    }
}