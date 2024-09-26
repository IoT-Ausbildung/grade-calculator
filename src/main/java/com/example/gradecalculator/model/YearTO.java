package com.example.gradecalculator.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class YearTO {
    private long id;
    private String name;
    private List<UserSubjectTO> subjects;

}
