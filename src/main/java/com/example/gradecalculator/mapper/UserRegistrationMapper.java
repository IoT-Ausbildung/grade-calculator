package com.example.gradecalculator.mapper;

import com.example.gradecalculator.entities.User;
import com.example.gradecalculator.entities.UserType;
import com.example.gradecalculator.web.model.UserSignUpTO;
import org.mapstruct.Mapper;
@Mapper
public abstract class UserRegistrationMapper {
    public abstract UserSignUpTO entityToTO(User user);
    public abstract User TOToEntity(UserSignUpTO registration);
    public UserType map(Long value){
        return new UserType(value);
    }
    public Long map(UserType value){
        return value.getId();
    }
}
