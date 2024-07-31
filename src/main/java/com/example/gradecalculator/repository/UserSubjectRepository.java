package com.example.gradecalculator.repository;

import com.example.gradecalculator.entities.UserSubject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface UserSubjectRepository extends JpaRepository<UserSubject, Long> {

    @Query("SELECT us FROM UserSubject us WHERE us.schoolYear.name = :year AND us.user.id = :userId")
    Set<UserSubject> findBySchoolYearAndUserId(@Param("year") int year, @Param("userId") Long userId);

    @Query("SELECT CASE WHEN COUNT(us) > 0 THEN true ELSE false END FROM UserSubject us WHERE us.subject.name = :subjectName")
    boolean existsBySubjectName(@Param("subjectName") String subjectName);
}

