package com.example.gradecalculator.repository;

import java.util.List;

import com.example.gradecalculator.entities.GradeSystem;
import com.example.gradecalculator.entities.GradeType;
import com.example.gradecalculator.entities.Subject;
import org.springframework.data.repository.CrudRepository;
public interface SubjectRepository extends CrudRepository<Subject, Long> {

    List<Subject> findByLastName(String lastName);

    Subject findById(long id);
}
