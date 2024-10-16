package com.example.gradecalculator.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GradeTO {
    private Long gradeTypeId;
    private String gradeTypeName;
    private int gradeValue;
    private Long userSubjectId;
    private String userSubjectName;
}
