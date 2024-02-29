package com.example.gradecalculator.repository;
import java.util.List;

import com.example.gradecalculator.entities.GradeSystem;
import org.springframework.data.repository.CrudRepository;
public interface GradeSystemRepository extends CrudRepository<GradeSystem, Long> {

    List<GradeSystem> findByLastName(String lastName);

    GradeSystem findById(long id);
}


