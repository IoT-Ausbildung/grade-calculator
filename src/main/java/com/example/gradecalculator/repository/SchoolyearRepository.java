package com.example.gradecalculator.repository;

import java.util.List;

import com.example.gradecalculator.entities.GradeType;
import com.example.gradecalculator.entities.Schoolyear;
import org.springframework.data.repository.CrudRepository;
public interface SchoolyearRepository extends CrudRepository<Schoolyear, Long> {
    
    Schoolyear findById(long id);
}