package com.example.gradecalculator.entities;

import jakarta.persistence.*;

@Entity
@Table( name = "grade_type")
public class GradeType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column( unique = true)
    private String name;

    private String description;
    private int weightage;

    @ManyToOne
    @JoinColumn(name = "grade_system")
    private GradeSystem gradeSystem;

    public GradeType(){
    }
    public GradeType(String name, String description, int weightage) {
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

    public int getWeightage() {
        return weightage;
    }

    public void setWeightage(int weightage) {
        this.weightage = weightage;
    }

    public GradeSystem getGradeSystem() {
        return gradeSystem;
    }

    public void setGradeSystem(GradeSystem gradeSystem) {
        this.gradeSystem = gradeSystem;
    }

    public Long getId(){return id;}

    public void setId(Long id){this.id = id;}

    public String getName(){return name;}

    public void setName(String name){this.name = name;}
}
