package com.example.gradecalculator.repository;

import org.springframework.data.repository.CrudRepository;

import com.example.gradecalculator.entities.UserType;

public interface UserTypeRepository extends CrudRepository<UserType, Long> {
    UserTypeRepository findById(long id);
}
