package com.example.gradecalculator.mapper;

import com.example.gradecalculator.entities.Subject;
import org.mapstruct.Mapper;

@Mapper
public abstract class SubjectMapper {
    public static Object toDTO(Subject subject) {
        return null;
    }
}
