package com.example.gradecalculator.repository;

import com.example.gradecalculator.entities.GradeType;
import org.springframework.data.repository.CrudRepository;

public interface GradeTypeRepository extends CrudRepository<GradeType, Long> {
    GradeType findByName(String name);
}