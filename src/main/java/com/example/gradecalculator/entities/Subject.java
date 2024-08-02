package com.example.gradecalculator.entities;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "subject")
public class Subject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userID;
    @Column(unique = true)
    private String name;

    private String description;

    private int creditValue;


    @OneToMany(mappedBy = "subject")
    private Set<UserSubject> userSubjects;

    public Subject() {
    }

    public Subject(String name, String description) {
        this.name = name;
        this.description = description;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public int getCreditValue() {
        return creditValue;
    }

    public void setCreditValue(int creditValue) {
        this.creditValue = creditValue;
    }

}
