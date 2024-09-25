
package com.example.gradecalculator.repository;

import com.example.gradecalculator.entities.UserGrade;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserGradeRepository extends JpaRepository<UserGrade, Long> {
    List<UserGrade> findByUserId(long userId);
}
