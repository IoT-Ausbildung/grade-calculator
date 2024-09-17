package com.example.gradecalculator.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.text.DecimalFormat;

@Getter
@Setter
@Entity
@Table(name = "grade_type")
public class GradeType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String name;

    private String description;
    @Column(precision = 5)
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
}