package com.example.gradecalculator.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Table(name = "user_subject")
@NoArgsConstructor
@Getter
@Setter
public class UserSubject implements Serializable {

    @Setter
    @Getter
    private String email;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "school_year_id", nullable = false)
    private SchoolYear schoolYear;

    @Setter
    @Getter
    @ManyToOne
    @JoinColumn(name = "subject_id", nullable = false)
    private Subject subject;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @OneToOne(mappedBy = "userSubject")
    private GradeType grade;

    public UserSubject(User selectedUser, Subject selectedSubject, SchoolYear selectedYear) {
        this.user = selectedUser;
        this.subject = selectedSubject;
        this.schoolYear = selectedYear;
    }
}

