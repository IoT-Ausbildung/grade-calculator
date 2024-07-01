package com.example.gradecalculator.repository;

import com.example.gradecalculator.entities.SchoolYear;
import org.springframework.data.repository.ListCrudRepository;

public interface SchoolYearRepository extends ListCrudRepository<SchoolYear, Long> {
    SchoolYear findById(long id);
}