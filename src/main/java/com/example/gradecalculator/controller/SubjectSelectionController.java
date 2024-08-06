package com.example.gradecalculator.controller;

import com.example.gradecalculator.entities.SchoolYear;
import com.example.gradecalculator.entities.Subject;
import com.example.gradecalculator.entities.User;
import com.example.gradecalculator.entities.UserSubject;
import com.example.gradecalculator.enums.Subjects;
import com.example.gradecalculator.repository.SchoolYearRepository;
import com.example.gradecalculator.repository.SubjectRepository;
import com.example.gradecalculator.repository.UserRepository;
import com.example.gradecalculator.repository.UserSubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/subjectselection")
public class SubjectSelectionController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private SchoolYearRepository schoolYearRepository;

    @Autowired
    private UserSubjectRepository userSubjectRepository;

    @GetMapping
    public List<String> getAllSubjects() {
        return Arrays.stream(Subjects.values())
                .map(Subjects::name)
                .collect(Collectors.toList());
    }

    @PostMapping("/save")
    public String saveSelectedSubjects(@RequestParam("schoolYear") String schoolYearName,
                                       @RequestParam("userId") Long userId,
                                       @RequestParam("subjects") String[] subjects,
                                       Model model) {
        try {

            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

            SchoolYear schoolYear = schoolYearRepository.findByName(schoolYearName)
                    .orElseThrow(() -> new RuntimeException("School year not found with name: " + schoolYearName));

            for (String subjectName : subjects) {
                Subject subject = subjectRepository.findByName(subjectName)
                        .orElseThrow(() -> new RuntimeException("Subject not found with name: " + subjectName));

                UserSubject userSubject = new UserSubject();
                userSubject.setUser(user);
                userSubject.setSchoolYear(schoolYear);
                userSubject.setSubject(subject);
                userSubjectRepository.save(userSubject);
            }

            model.addAttribute("message", "Subjects successfully saved!");
            return "success"; //

        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            return "error";
        }
    }

    @GetMapping("/selected")
    public String showSelectedSubjects(@RequestParam("year") String schoolYearName, @RequestParam("userId") Long userId, Model model) {
        try {
            SchoolYear schoolYear = schoolYearRepository.findByName(schoolYearName)
                    .orElseThrow(() -> new RuntimeException("School year not found with name: " + schoolYearName));

            Set<UserSubject> userSubjects = userSubjectRepository.findBySchoolYearNameAndUserId(schoolYearName, userId);
            Set<String> selectedSubjects = userSubjects.stream()
                    .map(userSubject -> userSubject.getSubject().getName())
                    .collect(Collectors.toSet());

            model.addAttribute("selectedSubjects", selectedSubjects);
            model.addAttribute("year", schoolYearName);
            model.addAttribute("userId", userId);
            return "selectedSubjects";

        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            return "error";
        }
    }
}




