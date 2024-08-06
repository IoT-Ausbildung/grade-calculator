package com.example.gradecalculator.controller;

import com.example.gradecalculator.entities.SchoolYear;
import com.example.gradecalculator.entities.Subject;
import com.example.gradecalculator.entities.User;
import com.example.gradecalculator.entities.UserSubject;
import com.example.gradecalculator.repository.SchoolYearRepository;
import com.example.gradecalculator.repository.SubjectRepository;
import com.example.gradecalculator.repository.UserRepository;
import com.example.gradecalculator.repository.UserSubjectRepository;
import com.example.gradecalculator.service.SubjectSelectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Set;

@Controller
public class SubjectController {

    @Autowired
    private final UserSubjectRepository userSubjectRepository;
    private final SchoolYearRepository schoolYearRepository;
    private final UserRepository userRepository;
    private final SubjectRepository subjectRepository;
    private final SubjectSelectionService subjectSelectionService;

    public SubjectController(UserSubjectRepository userSubjectRepository,
                             SchoolYearRepository schoolYearRepository,
                             UserRepository userRepository,
                             SubjectRepository subjectRepository,
                             SubjectSelectionService subjectSelectionService) {
        this.userSubjectRepository = userSubjectRepository;
        this.schoolYearRepository = schoolYearRepository;
        this.userRepository = userRepository;
        this.subjectRepository = subjectRepository;
        this.subjectSelectionService = subjectSelectionService;
    }

    @GetMapping("/userSubject/form")
    public String showUserSubjectForm(Model model) {
        List<SchoolYear> years = (List<SchoolYear>) schoolYearRepository.findAll();
        List<Subject> subjects = (List<Subject>) subjectRepository.findAll();
        model.addAttribute("years", years);
        model.addAttribute("subjects", subjects);
        return "userSubjectForm";
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

            subjectSelectionService.selectSubjectForYear(year, subjectName);

            UserSubject userSubject = new UserSubject(selectedUser, selectedSubject, selectedYear);
            userSubjectRepository.save(userSubject);
        }

        return "redirect:/grades";
    }

    @GetMapping("/userSubject/selected")
    public String showSelectedSubjects(@RequestParam("year") int year, Model model) {
        SubjectSelectionService subjectSelectionService = new SubjectSelectionService(userSubjectRepository) {
            @Override
            public void saveUserSubjects(String schoolYearName, Long userId, List<String> subjects) {

            }
            @Override
            public void getSelectedSubjectsForSchoolYear(String schoolYearName, Long userId) {

            }
        };
        Set<String> selectedSubjects = subjectSelectionService.getSelectedSubjectsForYear(year);
        model.addAttribute("selectedSubjects", selectedSubjects);
        model.addAttribute("year", year);
        return "selectedSubjects";
    }

}


