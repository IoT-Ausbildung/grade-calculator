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
    private Subject subject;
    private boolean selected;
    private List<SubjectTO> subjects;

    public SubjectTO(List<SubjectTO> subjects) {
        this.subjects = subjects;
    }

    public SubjectTO(Long id, String name, String description) {
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}

