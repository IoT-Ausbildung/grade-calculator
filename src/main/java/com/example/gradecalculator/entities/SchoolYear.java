package com.example.gradecalculator.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Getter
@Setter
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

    public void setSubjects(List<Subject> subjects) {
    }
}
