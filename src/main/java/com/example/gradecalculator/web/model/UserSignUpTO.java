package com.example.gradecalculator.web.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;


public class UserSignUpTO {
    private String firstName;
    private String lastName;
    @NotNull
    private String userName;
    @NotNull
    private String email;
    @NotNull
    private String encodedPassword;
    @NotNull
    private Long userType;

    public UserSignUpTO(){}
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getEncodedPassword() {
        return encodedPassword;
    }
    public void setEncodedPassword(String encodedPassword) {
        this.encodedPassword = encodedPassword;
    }
    public Long getUserType() {
        return userType;
    }
    public void setUserType(Long userType) {
        this.userType = userType;
    }
}
