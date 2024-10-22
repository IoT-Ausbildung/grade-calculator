package com.example.gradecalculator.enums;

public enum UserNames {
    TRAINEE("Trainee"),
    STUDENT("Student"),
    TRAINER("Trainer");
    private final String value;

    UserNames(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
