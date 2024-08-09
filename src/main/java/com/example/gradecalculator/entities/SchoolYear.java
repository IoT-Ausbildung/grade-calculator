package com.example.gradecalculator.entities;


import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.Year;
import java.util.List;
import java.util.Set;

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

    @OneToMany(mappedBy = "schoolYear")
    private Set<UserSubject> userSubjects;
    @OneToMany(mappedBy = "schoolYear")
    private Set<User> user;



    public SchoolYear(String name, LocalDate startDate, LocalDate endDate) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public SchoolYear(long id, String name) {
        this.id = id;
        this.name = name;
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

    public void setSubjects(List<Subject> subjects) {
    }

    public Year orElse(Object o) {
        return null;
    }

    public SchoolYear orElseThrow(Object yearNotFound) {
        return null;
    }
}
