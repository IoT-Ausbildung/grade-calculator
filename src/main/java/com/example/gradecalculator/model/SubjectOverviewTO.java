package com.example.gradecalculator.model;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class SubjectOverviewTO {
    private List<YearTO> years;
}
