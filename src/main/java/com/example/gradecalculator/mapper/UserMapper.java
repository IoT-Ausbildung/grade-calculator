package com.example.gradecalculator.mapper;

import com.example.gradecalculator.entities.User;
import com.example.gradecalculator.entities.UserType;
import com.example.gradecalculator.model.UserTO;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

@Mapper
public abstract class UserMapper {
    public abstract UserTO dataToTO(User user);
    public abstract Iterable<UserTO> dataToTO(Iterable<User> users);
    public abstract User TOToData(UserTO userTest);
    public UserType map(Long value){return new UserType(value);}
    public String map(UserType value){return value.getName();}
    @Named("mapUserType")
    UserType mapUserType(Long userTypeId) {
        return new UserType(userTypeId);
    }
}
