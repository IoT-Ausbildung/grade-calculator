package com.example.gradecalculator.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GradeTO {
    private Long gradeTypeId;
    private int gradeValue;
    private Long userSubjectId;
}
