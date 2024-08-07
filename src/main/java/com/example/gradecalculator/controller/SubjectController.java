package com.example.gradecalculator.controller;

import com.example.gradecalculator.entities.SchoolYear;
import com.example.gradecalculator.entities.Subject;
import com.example.gradecalculator.entities.User;
import com.example.gradecalculator.entities.UserSubject;
import com.example.gradecalculator.enums.Subjects;
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

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
public class SubjectController {

    @Autowired
    private final UserSubjectRepository userSubjectRepository;
    private final SchoolYearRepository schoolYearRepository;
    private final UserRepository userRepository;
    private final SubjectRepository subjectRepository;
    private final SubjectService subjectService;

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
        List<SchoolYear> years = (List<SchoolYear>) schoolYearRepository.findAll();
        List<Subject> subjects = (List<Subject>) subjectRepository.findAll();
        model.addAttribute("years", years);
        model.addAttribute("subjects", subjects);
        return "subjectSelection";
    }

    @PostMapping("/userSubject/save")
    public String saveUserSubject(
            @RequestParam("year") long yearId,
            @RequestParam("subject") long subjectId,
            @RequestParam("user") long userId
    ) {
        SchoolYear selectedYear = schoolYearRepository.findById(yearId);
        Subject selectedSubject = subjectRepository.findById(subjectId);
        User selectedUser = userRepository.findById(userId).orElse(null);

        if (selectedYear != null && selectedSubject != null && selectedUser != null) {
            String subjectName = selectedSubject.getName();
            int year = selectedYear.getStartDate().getYear();

            subjectService.selectSubjectForYear(year, subjectName);

            UserSubject userSubject = new UserSubject(selectedUser, selectedSubject, selectedYear);
            userSubjectRepository.save(userSubject);
        }

        return "redirect:/grades";
    }

    @GetMapping("/userSubject/selected")
    public String showSelectedSubjects(@RequestParam("year") int year, Model model) {
        SubjectService subjectService = new SubjectService(userSubjectRepository) {
            @Override
            public SubjectTO dataTO(Subject subject) {
                return null;
            }

            @Override
            public void saveUserSubjects(String schoolYearName, Long userId, List<String> subjects) {

            }
            @Override
            public void getSelectedSubjectsForSchoolYear(String schoolYearName, Long userId) {

            }
        };
        Set<String> selectedSubjects = subjectService.getSelectedSubjectsForYear(year);
        model.addAttribute("selectedSubjects", selectedSubjects);
        model.addAttribute("year", year);
        return "selectedSubjects";
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
    @PostMapping("/delete")
    public String deleteUserSubjectWithoutGrade(@RequestParam("userSubjectId") Long userSubjectId, Model model) {
        try {
            subjectService.deleteUserSubjectWithoutGrade(userSubjectId);
            model.addAttribute("message", "Subject successfully deleted!");
            return "success";
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            return "error";
        }
    }

}


