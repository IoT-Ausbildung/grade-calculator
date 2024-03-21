package com.example.gradecalculator.repository;

import com.example.gradecalculator.entities.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
    UserRepository findById(long id);
}
