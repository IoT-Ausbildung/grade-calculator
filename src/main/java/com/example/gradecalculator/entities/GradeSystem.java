package com.example.gradecalculator.entities;

import jakarta.persistence.*;

import java.util.List;


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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<GradeType> getGradeTypes() {
        return gradeTypes;
    }

    public void setGradeTypes(List<GradeType> gradeTypes) {
        this.gradeTypes = gradeTypes;
    }

}
