package com.example.gradecalculator.mapper;

import com.example.gradecalculator.entities.Subject;
import com.example.gradecalculator.model.SubjectTO;
import org.mapstruct.Mapper;

@Mapper
public abstract class SubjectMapper {
    public SubjectTO dataTO() {
        return dataTO(null);
    }

    public abstract SubjectTO dataTO(Subject subject);
}
