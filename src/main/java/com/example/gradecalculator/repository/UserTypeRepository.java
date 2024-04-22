package com.example.gradecalculator.repository;

import com.example.gradecalculator.enums.UserNames;
import org.springframework.data.repository.CrudRepository;

import com.example.gradecalculator.entities.UserType;

public interface UserTypeRepository extends CrudRepository<UserType, Long> {
    UserType findByName(UserNames name);
}
