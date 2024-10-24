package com.example.gradecalculator.mapper;

import com.example.gradecalculator.entities.UserGrade;
import com.example.gradecalculator.model.GradeTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface GradeMapper {
    @Mapping(source = "gradeType.id", target = "gradeTypeId")
    @Mapping(source = "gradeType.name", target = "gradeTypeName")
    @Mapping(source = "userSubject.id", target = "userSubjectId")
    @Mapping(source = "grade", target = "gradeValue")
    GradeTO userGradeToGradeTO(UserGrade userGrade);
}
