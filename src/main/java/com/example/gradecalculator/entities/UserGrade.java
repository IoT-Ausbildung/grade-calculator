package com.example.gradecalculator.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "user_grade")
public class UserGrade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_subject_id", nullable = false)
    private UserSubject userSubject;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "grade_type_id", nullable = false)
    private GradeType gradeType;

    @Column(nullable = false)
    private int grade;

    public UserGrade() {
    }

    public UserGrade(UserSubject userSubject, GradeType gradeType, int grade) {
        this.userSubject = userSubject;
        this.gradeType = gradeType;
        this.grade = grade;
    }
}
