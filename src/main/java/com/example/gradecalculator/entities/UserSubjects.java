package com.example.gradecalculator.entities;

import jakarta.persistence.*;

@Entity
@Table(name="user_subjects")
public class UserSubjects {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    private String UserType_ID;
    private String UserName;
    private String email;
    private String password;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserType_ID() {
        return UserType_ID;
    }

    public void setUserType_ID(String userType_ID) {
        UserType_ID = userType_ID;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}
