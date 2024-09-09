package com.example.gradecalculator.repository;

import com.example.gradecalculator.entities.SchoolYear;
import com.example.gradecalculator.entities.Subject;
import com.example.gradecalculator.entities.User;
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

    @Query("SELECT CASE WHEN COUNT(us) > 0 THEN true ELSE false END FROM UserSubject us WHERE us.subject.name = :subjectName")
    boolean existsBySubjectName(@Param("subjectName") String subjectName);

    @Query("SELECT us FROM UserSubject us WHERE us.schoolYear.name = :schoolYearName AND us.user.id = :userId")

    Set<UserSubject> findBySchoolYearNameAndUserId(@Param("schoolYearName") String schoolYearName, @Param("userId") Long userId);
    List<UserSubject> findByUserId(Long userId);

    boolean existsByUserAndSubjectAndSchoolYear(User selectedUser, Subject selectedSubject, SchoolYear selectedYear);
}



