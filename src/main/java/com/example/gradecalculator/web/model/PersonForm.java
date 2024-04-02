package com.example.gradecalculator.web.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class PersonForm {
    @NotNull
    @Size(min = 2, max = 30)
    private String name;

    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String toString() {
        return "Person(Name: " + this.name + ")";
    }
}
