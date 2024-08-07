package com.example.gradecalculator.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.Set;


@Entity
@Table(name = "application_user")
public class User {
    @Setter
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Setter
    @Getter
    private String firstName;
    @Setter
    @Getter
    private String lastName;
    @Setter
    @Getter
    private String userName;
    @Setter
    @Getter
    private String email;
    @Setter
    @Getter
    private String encodedPassword;

    @Setter
    @Getter
    @ManyToOne
    @JoinColumn(name = "user_type_id")
    private UserType userType;

    @ManyToOne
    @JoinColumn(name ="school_year_id")
    private SchoolYear schoolYear;

    @OneToMany(mappedBy = "user")
    private Set<UserSubject> userSubjects;

    public User() {
    }

    public User(String firstName, String lastName, String userName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.email = email;
    }

    public User(String encodedPassword) {
        this.encodedPassword = encodedPassword;
    }

    public User orElse(Object o) {
        return null;
    }
}