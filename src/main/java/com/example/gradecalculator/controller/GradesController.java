package com.example.gradecalculator.controller;

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

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

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

    @PostMapping("/save-grade")
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
    public ResponseEntity<List<GradeTO>> showGrades(Authentication authentication) {
        try {
            var userId = userService.getAuthenticatedUserId(authentication);
            List<GradeTO> gradeTOs = StreamSupport.stream(userGradeRepository.findByUserId(userId).spliterator(), false)
                    .map(gradeMapper::userGradeToGradeTO)
                    .filter(Objects::nonNull)
                    .sorted(Comparator.comparing(GradeTO::getGradeTypeName))
                    .collect(Collectors.toList());

            return ResponseEntity.ok(gradeTOs);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}



