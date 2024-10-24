package com.example.gradecalculator.mapper;

import com.example.gradecalculator.entities.User;
import com.example.gradecalculator.entities.UserType;
import com.example.gradecalculator.model.UserSignUpTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class UserRegistrationMapper {
    public abstract User TOToEntity(UserSignUpTO registration);

    public UserType map(Long value) {
        return new UserType(value);
    }
}
