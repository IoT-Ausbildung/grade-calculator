package com.example.gradecalculator.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserEditTO {
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
}
