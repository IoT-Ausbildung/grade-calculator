package com.example.gradecalculator.repository;

import java.util.List;

import com.example.gradecalculator.entities.GradeSystem;
import com.example.gradecalculator.entities.GradeType;
import org.springframework.data.repository.CrudRepository;
public interface GradeTypeRepository extends CrudRepository<GradeType, Long> {

    List<GradeType> findByLastName(String lastName);

    GradeType findById(long id);
}