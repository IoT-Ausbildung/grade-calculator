package com.example.gradecalculator.repository;

import com.example.gradecalculator.entities.GradeSystem;
import com.example.gradecalculator.entities.SchoolYear;
import org.springframework.data.repository.CrudRepository;
import java.util.List;
public interface SchoolYearRepository extends CrudRepository<SchoolYear, Long> {
    List<SchoolYear> findByGradeSystem(GradeSystem gradeSystem);
}