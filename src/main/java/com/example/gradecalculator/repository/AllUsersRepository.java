package com.example.gradecalculator.repository;

import com.example.gradecalculator.entities.AllUsers;
import org.springframework.data.repository.CrudRepository;

public interface AllUsersRepository extends CrudRepository<AllUsers, Long> {
}
