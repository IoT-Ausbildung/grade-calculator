package com.example.gradecalculator.controller;

import com.example.gradecalculator.entities.SchoolYear;
import com.example.gradecalculator.entities.Subject;
import com.example.gradecalculator.entities.User;
import com.example.gradecalculator.entities.UserSubject;
import com.example.gradecalculator.mapper.SubjectMapper;
import com.example.gradecalculator.mapper.UserMapper;
import com.example.gradecalculator.model.SubjectTO;
import com.example.gradecalculator.repository.SchoolYearRepository;
import com.example.gradecalculator.repository.SubjectRepository;
import com.example.gradecalculator.repository.UserRepository;
import com.example.gradecalculator.repository.UserSubjectRepository;
import com.example.gradecalculator.service.SubjectService;
import com.example.gradecalculator.service.UserService;
import org.mapstruct.factory.Mappers;
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
    public String saveUserSubject(@RequestParam("schoolYear") long yearId, @RequestParam("subjects") long subjectId, Authentication authentication, Model model) {
        try {
            SchoolYear selectedYear = schoolYearRepository.findById(yearId).orElseThrow(() -> new IllegalArgumentException("Year not found"));
            Subject selectedSubject = subjectRepository.findById(subjectId).orElseThrow(() -> new IllegalArgumentException("Subject not found"));
            var userID = userService.getAuthenticatedUserId(authentication);
            User selectedUser = userRepository.findById(userID).orElseThrow(() -> new IllegalArgumentException("User not found"));

            subjectService.selectSubjectForYear(selectedYear.getStartDate().getYear(), selectedSubject.getName());

            UserSubject userSubject = new UserSubject(selectedUser, selectedSubject, selectedYear);
            userSubjectRepository.save(userSubject);

            return "redirect:/userSubject/selected?year=" + selectedYear.getName() + "&user=" + userID;

        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "error";
        }
    }

    @GetMapping("userSubject/selected")
    public String showSelectedSubjects(Model model, Authentication authentication) {
        try {
            var userId = userService.getAuthenticatedUserId(authentication);

            var userSubjects = userSubjectRepository.findByUserId(userId);


            TreeMap<String, Set<String>> subjectsByYear = new TreeMap<>();
            for (UserSubject userSubject : userSubjects) {
                String year = userSubject.getSchoolYear().getName();
                String subject = userSubject.getSubject().getName();
                subjectsByYear.computeIfAbsent(year, k -> new TreeSet<>()).add(subject);
            }

            model.addAttribute("subjectsByYear", subjectsByYear);
            model.addAttribute("user", userId);

            return "userSubjects";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "error";
        }

    }
}