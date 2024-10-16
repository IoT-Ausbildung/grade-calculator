package com.example.gradecalculator.enums;

import lombok.Getter;


@Getter
public enum GradeTypes {
    OralMark("Oral Mark"),
    ClassTest("Class Test"),
    SchoolAssignment("School Assignment");

    private final String value;

    GradeTypes(String value) {
        this.value = value;
    }
}