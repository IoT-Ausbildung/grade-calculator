package com.example.gradecalculator.repository;

import com.example.gradecalculator.entities.SchoolYear;
import org.springframework.data.repository.ListCrudRepository;

import java.util.Optional;

public interface SchoolYearRepository extends ListCrudRepository<SchoolYear, Long> {
    Optional<SchoolYear> findById(Long id);
    Optional<SchoolYear> findByName(String name);
}