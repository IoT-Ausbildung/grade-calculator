package com.example.gradecalculator.repository;

import java.util.List;

import com.example.gradecalculator.entities.GradeSystem;
import com.example.gradecalculator.entities.UserType;
import org.springframework.data.repository.CrudRepository;
public interface UserTypeRepository extends CrudRepository<UserType, Long> {



    UserType findById(long id);
}
