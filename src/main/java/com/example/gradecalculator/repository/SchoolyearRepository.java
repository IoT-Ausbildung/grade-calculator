package com.example.gradecalculator.repository;

import com.example.gradecalculator.entities.SchoolYear;
import org.springframework.data.repository.CrudRepository;
public interface SchoolYearRepository extends CrudRepository<SchoolYear, Long> {



    SchoolYear findById(long id);
}