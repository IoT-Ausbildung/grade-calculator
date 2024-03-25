package com.example.gradecalculator.entities;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "school_year")
public class SchoolYear {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;
    private LocalDate startDate;

    private LocalDate endDate;
    @ManyToOne
    @JoinColumn(name = "grade_system_id")
    private GradeSystem gradeSystem;

    @OneToMany(mappedBy = "schoolYear")
    private List<Subject> subjects;

    public SchoolYear(String name, LocalDate startDate, LocalDate endDate, GradeSystem gradeSystem) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.gradeSystem = gradeSystem;
    }

    public SchoolYear(long id, String name, GradeSystem gradeSystem) {
        this.id = id;
        this.name = name;
        this.gradeSystem = gradeSystem;
    }

    public SchoolYear() {

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

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public GradeSystem getGradeSystem() {
        return gradeSystem;
    }

    public void setGradeSystem(GradeSystem gradeSystem) {
        this.gradeSystem = gradeSystem;
    }

    public List<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<Subject> subjects) {
        this.subjects = subjects;
    }
}
