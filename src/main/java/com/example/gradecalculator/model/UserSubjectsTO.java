package com.example.gradecalculator.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserSubjectsTO {

    private int userId;
    private List<SubjectTO> selectedSubjects;

    public UserSubjectsTO(int userId, List<SubjectTO> selectedSubjects, boolean termsAccepted) {
        this.userId = userId;
        this.selectedSubjects = selectedSubjects;
    }
}

