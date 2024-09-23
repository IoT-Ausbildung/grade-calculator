
package com.example.gradecalculator.repository;

import com.example.gradecalculator.entities.UserGrade;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserGradeRepository extends JpaRepository<UserGrade, Long> {
}
