package com.example.gradecalculator.repository;

import com.example.gradecalculator.entities.UserSubject;
import org.springframework.data.repository.CrudRepository;

public interface UserSubjectRepository extends CrudRepository <UserSubject,Long> {
}
