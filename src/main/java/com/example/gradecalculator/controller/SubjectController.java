package com.example.gradecalculator.controller;

import com.example.gradecalculator.entities.SchoolYear;
import com.example.gradecalculator.entities.Subject;
import com.example.gradecalculator.entities.User;
import com.example.gradecalculator.entities.UserSubject;
import com.example.gradecalculator.mapper.SubjectMapper;
import com.example.gradecalculator.model.SubjectTO;
import com.example.gradecalculator.model.UserSubjectTO;
import com.example.gradecalculator.repository.SchoolYearRepository;
import com.example.gradecalculator.repository.SubjectRepository;
import com.example.gradecalculator.repository.UserRepository;
import com.example.gradecalculator.repository.UserSubjectRepository;
import com.example.gradecalculator.service.SubjectService;
import com.example.gradecalculator.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
                             SubjectRepository subjectRepository, SchoolYearRepository schoolYearRepository,
                             SubjectMapper subjectMapper, UserService userService, UserRepository userRepository) {
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
    public ResponseEntity<Map<String, Object>> saveUserSubject(@RequestParam(value = "selectedValues", required = false)
                                                                   String[] selectedValues, Authentication authentication) {
        Map<String, Object> response = new TreeMap<>();
        try {
            if (selectedValues == null || selectedValues.length == 0) {
                response.put("success", false);
                response.put("error", "No subjects selected.");
                return ResponseEntity.badRequest().body(response);
            }
            var userId = userService.getAuthenticatedUserId(authentication);
            var errorList = subjectService.saveSubjects(selectedValues, userId);
            if(errorList.isEmpty()) {
                response.put("success", true);
                return ResponseEntity.ok(response);
            }
            response.put("success", false);
            response.put("errors", errorList);
            return ResponseEntity.badRequest().body(response);

        } catch (IllegalArgumentException e) {
            response.put("success", false);
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping("userSubject/selected")
    public String showSelectedSubjects(Model model, Authentication authentication) {
        try {
            var userId = userService.getAuthenticatedUserId(authentication);
            var subjectsByYear = subjectService.selectedSubject(userId);

            model.addAttribute("subjectsByYear", subjectsByYear);
            model.addAttribute("user", userId);

            return "userSubjects";

        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());

            return "error";
        }
    }


    @DeleteMapping("/subject/delete/{ID}")
    public ResponseEntity<Void> deleteSubject(@PathVariable Long ID, Authentication authentication) {
        var userId = userService.getAuthenticatedUserId(authentication);

        boolean deleted = subjectService.deleteSubject(ID, String.valueOf(userId));

        if (deleted) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }
}