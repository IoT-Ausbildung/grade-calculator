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
    private BigDecimal weightage;

    @ManyToOne
    @JoinColumn(name = "grade_system")
    private GradeSystem gradeSystem;

    public GradeType(long l, String description, GradeSystem gradeSystem) {
    }

    public GradeType() {

    }

    public String getWeightagePercentage() {
        DecimalFormat df = new DecimalFormat("#.##");
        BigDecimal weightagePercentage = weightage.multiply(BigDecimal.valueOf(100));
        return df.format(weightagePercentage) + "%";
    }

    public GradeType(String name, String description, BigDecimal weightage) {
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

    public BigDecimal getWeightage() {
        return weightage;
    }

    public void setWeightage(int weightage) {
        this.weightage = BigDecimal.valueOf(weightage);
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
