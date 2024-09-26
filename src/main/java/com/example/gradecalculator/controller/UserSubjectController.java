package com.example.gradecalculator.controller;

import com.example.gradecalculator.entities.GradeType;
import com.example.gradecalculator.entities.SchoolYear;
import com.example.gradecalculator.entities.UserSubject;
import com.example.gradecalculator.model.SubjectOverviewTO;
import com.example.gradecalculator.model.SubjectTO;
import com.example.gradecalculator.model.UserSubjectTO;
import com.example.gradecalculator.repository.GradeTypeRepository;
import com.example.gradecalculator.repository.SchoolYearRepository;
import com.example.gradecalculator.repository.UserSubjectRepository;
import com.example.gradecalculator.service.GradeService;
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
public class UserSubjectController {

    private final SubjectService subjectService;
    ;
    private final SchoolYearRepository schoolYearRepository;
    private final UserService userService;
    private final UserSubjectRepository userSubjectRepository;
    private final GradeTypeRepository gradeTypeRepository;
    private final GradeService gradeService;

    @Autowired
    public UserSubjectController(SubjectService subjectService, SchoolYearRepository schoolYearRepository,
                                 UserService userService, UserSubjectRepository userSubjectRepository, GradeTypeRepository gradeTypeRepository, GradeService gradeService) {
        this.schoolYearRepository = schoolYearRepository;
        this.subjectService = subjectService;
        this.userService = userService;
        this.userSubjectRepository = userSubjectRepository;
        this.gradeTypeRepository = gradeTypeRepository;
        this.gradeService = gradeService;
    }

    @GetMapping("/userSubject/form")
    public String showUserSubjectForm(Model model) {
        List<SchoolYear> years = schoolYearRepository.findAll();
        List<SubjectTO> subjects = subjectService.getAllSubjects();
        model.addAttribute("years", years);
        model.addAttribute("subjects", subjects);
        model.addAttribute("selectedSubjects", new ArrayList<UserSubject>());

        return "subjectSelection";
    }

    @PostMapping("/userSubject/save")
    public ResponseEntity<Map<String, Object>> saveUserSubject(
            @RequestParam(value = "selectedValues", required = false) String[] selectedValues,
            Authentication authentication) {
        Map<String, Object> response = new TreeMap<>();
        try {
            if (selectedValues == null || selectedValues.length == 0) {
                response.put("errors", List.of("No subjects selected."));
                return ResponseEntity.badRequest().body(response);
            }

            var userId = userService.getAuthenticatedUserId(authentication);
            var errorList = subjectService.saveSubjects(selectedValues, userId);

            if (errorList.isEmpty()) {
                return ResponseEntity.ok().build();
            }

            response.put("errors", errorList);
            return ResponseEntity.badRequest().body(response);

        } catch (IllegalArgumentException e) {
            response.put("errors", List.of(e.getMessage()));
            return ResponseEntity.badRequest().body(response);
        } catch (Exception e) {
            response.put("errors", List.of("An unexpected error occurred."));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/userSubject/selected")
    public String showSelectedSubjects(Model model, Authentication authentication) {
        try {
            Long userId = userService.getAuthenticatedUserId(authentication);
            List<GradeType> gradeTypes = (List<GradeType>) gradeTypeRepository.findAll();

            var subjectOverview = subjectService.getUserSubjectsWithGrades(userId);

            model.addAttribute("subjectOverview", subjectOverview);
            model.addAttribute("user", userId);
            model.addAttribute("gradeTypes", gradeTypes);

            return "userSubjects";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "error";
        }
    }

    @DeleteMapping("/userSubject/delete/{ID}")
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