package com.example.gradecalculator.model;

import com.example.gradecalculator.enums.Subjects;

public class SubjectTO {
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

