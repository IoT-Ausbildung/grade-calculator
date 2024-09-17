package com.example.gradecalculator.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "grade_system")
public class GradeSystem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String name;

    private String description;

    @OneToMany(mappedBy = "gradeSystem")
    private List<GradeType> gradeTypes;

    public GradeSystem(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public GradeSystem() {

    }
}