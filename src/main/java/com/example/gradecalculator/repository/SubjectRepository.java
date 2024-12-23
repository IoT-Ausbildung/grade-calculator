package com.example.gradecalculator.repository;

import com.example.gradecalculator.entities.Subject;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository

public interface SubjectRepository extends CrudRepository<Subject, Long> {
    Optional<Subject> findById(Long id);
    Optional<Subject> findByName(String name);
}