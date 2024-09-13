package com.example.gradecalculator.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Setter
@Getter
@Entity
@Table(name = "subject")
public class Subject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    private String description;

    @OneToMany(mappedBy = "subject")
    private Set<UserSubject> userSubjects;

    public Subject() {
    }

    public Subject(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
