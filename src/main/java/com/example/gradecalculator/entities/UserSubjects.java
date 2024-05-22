package com.example.gradecalculator.entities;

import jakarta.persistence.*;

@Entity
@Table(name="user_subjects")
public class UserSubjects {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    private Integer UserSubject_ID;
    private Integer User_ID;
    private Integer SchoolYear;
    private Integer Subject_ID;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getUserSubject_ID() {
        return UserSubject_ID;
    }

    public void setUserSubject_ID(Integer userSubject_ID) {
        UserSubject_ID = userSubject_ID;
    }

    public Integer getUser_ID() {
        return User_ID;
    }

    public void setUser_ID(Integer user_ID) {
        User_ID = user_ID;
    }

    public Integer getSchoolYear() {
        return SchoolYear;
    }

    public void setSchoolYear(Integer schoolYear) {
        SchoolYear = schoolYear;
    }

    public Integer getSubject_ID() {
        return Subject_ID;
    }

    public void setSubject_ID(Integer subject_ID) {
        Subject_ID = subject_ID;
    }
}
