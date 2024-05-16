package com.example.gradecalculator.repository;

import com.example.gradecalculator.entities.GradeSystem;
import com.example.gradecalculator.entities.GradeType;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface GradeTypeRepository extends CrudRepository<GradeType, Long> {
    List<GradeType> findByGradeSystem(GradeSystem gradeSystem);
}