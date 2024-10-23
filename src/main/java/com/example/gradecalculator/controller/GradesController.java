package com.example.gradecalculator.controller;

import com.example.gradecalculator.enums.GradeTypes;
import com.example.gradecalculator.mapper.GradeMapper;
import com.example.gradecalculator.model.GradeTO;
import com.example.gradecalculator.repository.UserGradeRepository;
import com.example.gradecalculator.service.GradeService;
import com.example.gradecalculator.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.slf4j.helpers.Reporter.error;

@RestController
@RequestMapping("/grades")
public class GradesController {

    private final GradeService gradeService;
    private final UserService userService;
    private final UserGradeRepository userGradeRepository;
    private final GradeMapper gradeMapper;

    @Autowired
    public GradesController(GradeService gradeService, UserService userService, UserGradeRepository userGradeRepository, GradeMapper gradeMapper) {
        this.gradeService = gradeService;
        this.userService = userService;
        this.userGradeRepository = userGradeRepository;
        this.gradeMapper = gradeMapper;
    }

    @PostMapping("/add")
    public ResponseEntity<?> saveGrade(@RequestBody GradeTO gradeTO, Authentication authentication) {
        try {
            var userId = userService.getAuthenticatedUserId(authentication);
            gradeService.saveGrade(gradeTO, userId);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error saving grade");
        }
    }


    @GetMapping("/userGrades")
    public ResponseEntity<?> showGrades(Authentication authentication) {
        try {
            var userId = userService.getAuthenticatedUserId(authentication);
            Map<GradeTypes, List<GradeTO>> gradeTOs = gradeService.getAllGrades(userId);
            return ResponseEntity.ok(gradeTOs);

        } catch (Exception e) {
            error("Error occurred while fetching user grades", e);
            Map<String, String> errorResponse = Collections.singletonMap("error", "An error occurred while fetching grades. Please try again later.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
}




