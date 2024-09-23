package com.example.gradecalculator.enums;

public enum GradeTypes {
    OralMark("Oral mark"),
    ClassTest("Class test"),
    SchoolAssignment("School assignment");

    private final String value;

    GradeTypes(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}