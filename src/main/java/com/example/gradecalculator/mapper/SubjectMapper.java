package com.example.gradecalculator.mapper;

import com.example.gradecalculator.entities.Subject;
import com.example.gradecalculator.entities.UserSubject;
import com.example.gradecalculator.model.SubjectTO;
import com.example.gradecalculator.model.UserSubjectsTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface SubjectMapper {
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "description", source = "description")
    SubjectTO subjectToSubjectTO(Subject subject);
    List<SubjectTO> userSubjectsToSubjectTO(List<Subject> subjects);
    default List<SubjectTO> mapUserSubjectsToSubjectTOs(Set<UserSubject> userSubjects) {
        return userSubjects.stream()
                .map(UserSubject::getSubject)
                .map(this::subjectToSubjectTO)
                .collect(Collectors.toList());
    }

    default UserSubjectsTO userSubjectsToSubjectTO(Set<UserSubject> userSubjects, Long userId) {
        List<SubjectTO> subjectTOs = mapUserSubjectsToSubjectTOs(userSubjects);
        return new UserSubjectsTO(userId.intValue(), subjectTOs, true);
    }

    List<SubjectTO> userSubjectsToSubjectTOs(List<Subject> subjects);
}