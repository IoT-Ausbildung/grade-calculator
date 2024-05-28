package com.example.gradecalculator.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;


public class UserEditTO {
    @Getter
    @Setter
    @NotBlank
    private String firstName;

    @Getter
    @Setter
    @NotBlank
    private String lastName;
}