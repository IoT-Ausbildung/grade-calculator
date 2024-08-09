package com.example.gradecalculator.controller;

import com.example.gradecalculator.entities.SchoolYear;
import com.example.gradecalculator.entities.Subject;
import com.example.gradecalculator.entities.User;
import com.example.gradecalculator.entities.UserSubject;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

    @GetMapping("/userSubject/form")
    public String showUserSubjectForm(Model model) {
        List<SchoolYear> years = schoolYearRepository.findAll();
        List<Subject> subjects = (List<Subject>) subjectRepository.findAll();
        model.addAttribute("years", years);
        model.addAttribute("subjects", subjects);
        return "subjectSelection";
    }

    @PostMapping("/userSubject/save")
    public String saveUserSubject(@RequestParam("year") long yearId,
                                  @RequestParam("subject") long subjectId,
                                  @RequestParam("user") long userId,
                                  Model model) {
        try {
            SchoolYear selectedYear = schoolYearRepository.findById(yearId).orElseThrow(() -> new IllegalArgumentException("Year not found"));
            Subject selectedSubject = subjectRepository.findById(subjectId).orElseThrow(() -> new IllegalArgumentException("Subject not found"));
            User selectedUser = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));

            String subjectName = selectedSubject.getName();
            int year = selectedYear.getStartDate().getYear();

            subjectService.selectSubjectForYear(year, subjectName);

            UserSubject userSubject = new UserSubject(selectedUser, selectedSubject, selectedYear);
            userSubjectRepository.save(userSubject);

            return "redirect:/grades";

        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "error";
        }
    }

    @GetMapping("/userSubject/selected")
    public String showSelectedSubjects(@RequestParam("year") int year, Model model) {
        Set<String> selectedSubjects = subjectService.getSelectedSubjectsForYear(year);
        model.addAttribute("selectedSubjects", selectedSubjects);
        model.addAttribute("year", year);
        return "selectedSubjects";
    }

    @GetMapping("/subjects")
    public String getSubjects(Model model) {
        List<SubjectTO> subjects = subjectService.getAllSubjects();
        model.addAttribute("subjects", subjects);
        return "subjectSelection";
    }

    @PostMapping("/save")
    public String saveSelectedSubjects(@RequestParam("schoolYear") String schoolYearName,
                                       @RequestParam("userId") Long userId,
                                       @RequestParam("subjects") String[] subjects,
                                       Model model) {
        try {
            User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found with id: " + userId));
            SchoolYear schoolYear = schoolYearRepository.findByName(schoolYearName).orElseThrow(() -> new IllegalArgumentException("School year not found with name: " + schoolYearName));

            for (String subjectName : subjects) {
                Subject subject = subjectRepository.findByName(subjectName).orElseThrow(() -> new IllegalArgumentException("Subject not found with name: " + subjectName));

                UserSubject userSubject = new UserSubject();
                userSubject.setUser(user);
                userSubject.setSchoolYear(schoolYear);
                userSubject.setSubject(subject);
                userSubjectRepository.save(userSubject);
            }

            model.addAttribute("message", "Subjects successfully saved!");
            return "success";

        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "error";
        }
    }

    @GetMapping("/selected")
    public String showSelectedSubjects(@RequestParam("year") String schoolYearName,
                                       @RequestParam("userId") Long userId,
                                       Model model) {
        try {
            SchoolYear schoolYear = schoolYearRepository.findByName(schoolYearName).orElseThrow(() -> new IllegalArgumentException("School year not found with name: " + schoolYearName));

            Set<UserSubject> userSubjects = userSubjectRepository.findBySchoolYearNameAndUserId(schoolYearName, userId);
            Set<String> selectedSubjects = userSubjects.stream()
                    .map(userSubject -> userSubject.getSubject().getName())
                    .collect(Collectors.toSet());

            model.addAttribute("selectedSubjects", selectedSubjects);
            model.addAttribute("year", schoolYearName);
            model.addAttribute("userId", userId);
            return "selectedSubjects";

        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "error";
        }
    }
}
