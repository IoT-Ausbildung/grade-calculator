package com.example.gradecalculator.enums;

public enum Subjects {
    ITT1("ITT1"),
    ITT2("ITT2"),
    ITS("ITS"),
    AP("AP"),
    BGP("BGP"),
    Englisch("Englisch"),
    Deutsch("Deutsch"),
    Sport("Sport"),
    PuG("PuG");

    private final String value;

    Subjects(String value) {
        this.value=value;
    }

    public String getValue() {
        return value;
    }
}
