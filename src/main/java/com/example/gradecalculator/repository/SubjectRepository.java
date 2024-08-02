package com.example.gradecalculator.repository;

import com.example.gradecalculator.entities.Subject;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository

public interface SubjectRepository extends JpaRepository<Subject, Long> {
    Subject findById(long id);
    @Modifying
    @Transactional
    void deleteByIdAndName(long Id, String subjectName);
    Optional<Subject> findByName(String name);
}