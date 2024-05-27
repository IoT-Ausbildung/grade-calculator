package com.example.gradecalculator.repository;

import com.example.gradecalculator.entities.UserSubjects;
import org.springframework.data.repository.CrudRepository;

public interface UserSubjectsRepository extends CrudRepository <UserSubjects,Long> {
}
