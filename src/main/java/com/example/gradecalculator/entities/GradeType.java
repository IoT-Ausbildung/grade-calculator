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
    @Column(precision = 5)
    private Double weightage;

    public GradeType(String name) {
        this.name = name;
    }

    public GradeType() {
    }

    public String getWeightagePercentage() {
        DecimalFormat df = new DecimalFormat("#.##");
        return df.format(weightage * 100) + "%";
    }
}