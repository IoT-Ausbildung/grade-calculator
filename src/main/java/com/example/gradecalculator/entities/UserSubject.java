package com.example.gradecalculator.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "user_subject")
public class UserSubject {

    @ManyToOne
    @JoinColumn(name="school_year_id", nullable=false)
    private SchoolYear schoolYear;

    @ManyToOne
    @JoinColumn(name="subject_id", nullable=false)
    private Subject subject;

    @ManyToOne
    @JoinColumn(name = "application_user_id", nullable = false)
    private User user;

    @Setter
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    public UserSubject(User selectedUser, Subject selectedSubject, SchoolYear selectedYear) {
    }
}
