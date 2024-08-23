package com.example.gradecalculator.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.Set;

@Setter
@Getter
@Entity
@Table(name = "application_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;

    private String lastName;

    private String userName;

    private String email;

    private String encodedPassword;

    @ManyToOne
    @JoinColumn(name = "user_type_id")
    private UserType userType;

    @ManyToOne
    @JoinColumn(name = "school_year_id")
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

    public User orElseThrow(Object userNotFound) {
        return null;
    }

    // Add setters and getters or ensure Lombok works
    public String getEncodedPassword() {
        return encodedPassword;
    }

    public void setEncodedPassword(String encodedPassword) {
        this.encodedPassword = encodedPassword;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }
}
