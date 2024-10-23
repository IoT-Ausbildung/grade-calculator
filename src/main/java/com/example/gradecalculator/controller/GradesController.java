package com.example.gradecalculator.controller;

import com.example.gradecalculator.model.GradeTO;
import com.example.gradecalculator.service.GradeService;
import com.example.gradecalculator.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/grades")
public class GradesController {

    private final GradeService gradeService;
    private final UserService userService;

    @Autowired
    public GradesController(GradeService gradeService, UserService userService) {
        this.gradeService = gradeService;
        this.userService = userService;
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
}