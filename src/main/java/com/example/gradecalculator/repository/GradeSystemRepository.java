package com.example.gradecalculator.repository;

import com.example.gradecalculator.entities.GradeSystem;
import org.springframework.data.repository.CrudRepository;

public interface GradeSystemRepository extends CrudRepository<GradeSystem, Long> {
    GradeSystem findByName(String name);
}


