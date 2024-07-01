package com.example.gradecalculator.repository;

import com.example.gradecalculator.entities.UserSubject;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserSubjectRepository extends CrudRepository <UserSubject,Long> {
    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM User u WHERE u.userSubjects = :subject")
    boolean existsByEmail(@Param("subject") String subject);

}
