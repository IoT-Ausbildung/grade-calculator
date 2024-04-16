package com.example.gradecalculator.repository;

import com.example.gradecalculator.entities.User;
import com.example.gradecalculator.web.model.LoginDTO;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM User u WHERE u.email = :email")
    boolean existsByEmail(@Param("email") String email);

    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM User u WHERE u.userName = :userName")
    boolean existsByUserName(@Param("userName") String userName);

    User findById(long id);

    User findByUserName(String userName);

    User findByEmail(String email);
    @Query("SELECT u FROM User u WHERE u.email = :email AND u.encodedPassword = :password")
    User findByEmailAndPassword(@Param("email") String email, @Param("password") String password);

}
