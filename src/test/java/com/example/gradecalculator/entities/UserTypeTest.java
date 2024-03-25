package com.example.gradecalculator.entities;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class UserTypeTest {

    @Test
    public void testGetId() {
        UserType userType = new UserType();
        userType.setId(1L);
        Assertions.assertEquals(1L, userType.getId());
    }

    @Test
    public void testGetName() {
        UserType userType = new UserType();
        userType.setName("Admin");
        Assertions.assertEquals("Admin", userType.getName());
    }

    @Test
    public void testSetName() {
        UserType userType = new UserType();
        userType.setName("Admin");
        userType.setName("Super Admin");
        Assertions.assertEquals("Super Admin", userType.getName());
    }

    @Test
    public void testGetDescription() {
        UserType userType = new UserType();
        userType.setDescription("Administrator user type");
        Assertions.assertEquals("Administrator user type", userType.getDescription());
    }

    @Test
    public void testSetDescription() {
        UserType userType = new UserType();
        userType.setDescription("Administrator user type");
        userType.setDescription("Super user type");
        Assertions.assertEquals("Super user type", userType.getDescription());
    }

    @Test
    public void testGetUsers() {
        List<User> users = new ArrayList<>();
        users.add(new User());
        users.add(new User());
        UserType userType = new UserType();
        userType.setUsers(users);
        Assertions.assertEquals(users, userType.getUsers());
    }

    @Test
    public void testSetUsers() {
        List<User> users1 = new ArrayList<>();
        users1.add(new User());
        List<User> users2 = new ArrayList<>();
        users2.add(new User());
        UserType userType = new UserType();
        userType.setUsers(users1);
        userType.setUsers(users2);
        Assertions.assertEquals(users2, userType.getUsers());
    }
}