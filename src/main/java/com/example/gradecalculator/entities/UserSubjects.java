package com.example.gradecalculator.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="user_subjects")
public class UserSubjects {

    @Setter
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Setter
    @Getter
    private Integer UserSubject_ID;
    @Setter
    @Getter
    private Integer User_ID;
    @Setter
    @Getter
    private Integer SchoolYear;
    @Setter
    @Getter
    private Integer Subject_ID;

    @ManyToOne
    @JoinColumn(name = "grade_system_id")
    private GradeSystem gradeSystem;

    public void setName(String subjects) {
    }
}
