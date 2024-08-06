package com.example.gradecalculator.model;

import com.example.gradecalculator.enums.Subjects;
import lombok.Getter;
import lombok.Setter;

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
    @Getter
    @Setter
    private int creditValue;

    private Subjects subject;
    private boolean selected;

    public Subjects getSubject() {
        return subject;
    }

    public void setSubject(Subjects subject) {
        this.subject = subject;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}

