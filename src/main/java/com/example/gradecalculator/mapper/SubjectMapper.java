package com.example.gradecalculator.mapper;

import com.example.gradecalculator.entities.Subject;
import com.example.gradecalculator.model.SubjectTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SubjectMapper {
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "description", source = "description")
    SubjectTO subjectToSubjectTO(Subject subject);
}