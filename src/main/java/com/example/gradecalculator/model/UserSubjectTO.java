package com.example.gradecalculator.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class UserSubjectTO {
    private Long id;
    private String name;
    // TODO: should be grouped by grade type
    private List<GradeTO> grades;
}
