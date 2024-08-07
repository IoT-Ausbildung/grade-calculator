package com.example.gradecalculator.controller;

import com.example.gradecalculator.entities.SchoolYear;
import com.example.gradecalculator.entities.Subject;
import com.example.gradecalculator.entities.User;
import com.example.gradecalculator.entities.UserSubject;
import com.example.gradecalculator.enums.Subjects;
import com.example.gradecalculator.mapper.SubjectSelectionImp;
import com.example.gradecalculator.repository.SchoolYearRepository;
import com.example.gradecalculator.repository.SubjectRepository;
import com.example.gradecalculator.repository.UserRepository;
import com.example.gradecalculator.repository.UserSubjectRepository;
import com.example.gradecalculator.service.SubjectSelectionService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/subjectSelection")
public class SubjectSelectionController {

    @Getter
    private final SubjectSelectionService subjectSelectionService;
    private final UserRepository userRepository;
    private final SubjectRepository subjectRepository;
    private final SchoolYearRepository schoolYearRepository;
    private final UserSubjectRepository userSubjectRepository;
    @Getter
    private final SubjectSelectionImp subjectSelectionImp;

    @Autowired
    public SubjectSelectionController(SubjectSelectionService subjectSelectionService,
                                      SubjectSelectionImp subjectSelectionImp,
                                      UserRepository userRepository,
                                      SubjectRepository subjectRepository,
                                      SchoolYearRepository schoolYearRepository,
                                      UserSubjectRepository userSubjectRepository) {
        this.subjectSelectionService = subjectSelectionService;
        this.userRepository = userRepository;
        this.subjectRepository = subjectRepository;
        this.schoolYearRepository = schoolYearRepository;
        this.userSubjectRepository = userSubjectRepository;
        this.subjectSelectionImp = subjectSelectionImp;
    }

    @GetMapping
    public String getSubjects(Model model) {
        List<String> subjects = Arrays.stream(Subjects.values())
                .map(Subjects::name)
                .collect(Collectors.toList());
        model.addAttribute("subjects", subjects);
        return "subjectSelection";
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
            return "success";

        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            return "error";
        }
    }

    @GetMapping("/selected")
    public String showSelectedSubjects(@RequestParam("year") String schoolYearName,
                                       @RequestParam("userId") Long userId,
                                       Model model) {
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
