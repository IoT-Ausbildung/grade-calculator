package com.example.gradecalculator.model;

import com.example.gradecalculator.entities.Subject;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class SubjectTO {


    @Getter
    @Setter
    private int id;

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private String description;
    @Setter
    @Getter
    private Subject subject;
    @Setter
    @Getter
    private boolean selected;
    @Getter
    @Setter
    private String userSubject;

    private List<SubjectTO> subjects;


    public SubjectTO(Long id, String name, String description) {
    }

}

