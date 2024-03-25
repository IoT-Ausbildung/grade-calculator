package com.example.gradecalculator.entities;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.text.DecimalFormat;

@Entity
@Table(name = "grade_type")
public  class GradeType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String name;

    private String description;
    @Column(precision = 5, scale = 2)
    private Double weightage;

    @ManyToOne
    @JoinColumn(name = "grade_system")
    private GradeSystem gradeSystem;

    public GradeType() {
    }

    public GradeType(long id, String description, GradeSystem gradeSystem) {
        this.id = id;
        this.description = description;
        this.gradeSystem = gradeSystem;
    }

    public String getWeightagePercentage() {
        DecimalFormat df = new DecimalFormat("#.##");
        return df.format(weightage * 100) + "%";
    }

    public GradeType(String name, String description, Double weightage) {
        this.name = name;
        this.description = description;
        this.weightage = weightage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getWeightage() {
        return weightage;
    }

    public void setWeightage(Double weightage) {
        this.weightage = weightage;
    }

    public GradeSystem getGradeSystem() {
        return gradeSystem;
    }

    public void setGradeSystem(GradeSystem gradeSystem) {
        this.gradeSystem = gradeSystem;
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
}
