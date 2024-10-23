package com.example.gradecalculator.model;

import com.example.gradecalculator.entities.Subject;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class SubjectTO {
    private int id;
    private String name;
    private String description;
    private Subject subject;
    private boolean selected;
    private String userSubject;
}

