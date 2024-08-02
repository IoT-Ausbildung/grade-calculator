package com.example.gradecalculator.config;

import com.example.gradecalculator.dto.SubjectTO;
import com.example.gradecalculator.enums.Subjects;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

@Configuration
public class SubjectConfig {

    @Bean
    public List<SubjectTO> subjectTOs() {
        return Arrays.asList(
                new SubjectTO(Subjects.MATH, false),
                new SubjectTO(Subjects.SCIENCE, false),
                new SubjectTO(Subjects.ENGLISH, false),
                new SubjectTO(Subjects.HISTORY, false),
                new SubjectTO(Subjects.ART, false)
        );
    }
}
