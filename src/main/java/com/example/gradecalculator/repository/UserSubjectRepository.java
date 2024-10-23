package com.example.gradecalculator.repository;

import com.example.gradecalculator.entities.UserSubject;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface UserSubjectRepository extends CrudRepository<UserSubject, Long> {

    @Query("SELECT us FROM UserSubject us WHERE us.schoolYear.name = :year AND us.user.id = :userId")
    Set<UserSubject> findBySchoolYearAndUserId(@Param("year") int schoolYear, @Param("userId") Long userId);

    List<UserSubject> findByUserId(Long userId);

    void deleteAllByUserId(Long userId);
}



