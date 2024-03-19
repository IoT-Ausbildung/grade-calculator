package com.example.gradecalculator.repository;

import org.springframework.data.repository.CrudRepository;
import com.example.gradecalculator.entities.Subject;

public interface SubjectRepository extends CrudRepository<Subject, Long> {
    Subject findByName(String name);
}