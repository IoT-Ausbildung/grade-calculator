package com.example.gradecalculator.repository;

import com.example.gradecalculator.entities.GradeType;
import org.springframework.data.repository.CrudRepository;
import java.util.Optional;

public interface GradeTypeRepository extends CrudRepository<GradeType, Long> {
    Optional<GradeType> findByName(String name);
}