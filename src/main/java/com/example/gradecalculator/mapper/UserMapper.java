package com.example.gradecalculator.mapper;

import com.example.gradecalculator.entities.User;
import com.example.gradecalculator.entities.UserType;
import com.example.gradecalculator.enums.UserNames;
import com.example.gradecalculator.web.model.UserTO;
import org.mapstruct.Mapper;

@Mapper
public abstract class UserMapper {
    public abstract UserTO dataToTO(User user);
    public abstract Iterable<UserTO> dataToTO(Iterable<User> users);
    public abstract User TOToData(UserTO userTest);
    public UserType map(Long value){return new UserType(value);}
    public String map(UserType value){return value.getName();}
}
