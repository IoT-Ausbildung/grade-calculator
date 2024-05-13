package com.example.gradecalculator.repository;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

import com.example.gradecalculator.entities.GradeSystem;
import com.example.gradecalculator.entities.GradeType;

public interface GradeTypeRepository extends CrudRepository<GradeType, Long> {
    List<GradeType> findByGradeSystem(GradeSystem gradeSystem);
}