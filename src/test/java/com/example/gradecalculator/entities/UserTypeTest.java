package com.example.gradecalculator.entities;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class UserTypeTest {

    @Test
    public void testGetId() {
        UserType userType = new UserType("Admin", "Administrator");
        userType.setId(1L);
        assertEquals(1L, userType.getId().longValue());
    }

    @Test
    public void testGetName() {
        UserType userType = new UserType("Admin", "Administrator");
        assertEquals("Admin", userType.getName());
    }

    @Test
    public void testSetName() {
        UserType userType = new UserType("Admin", "Administrator");
        userType.setName("SuperAdmin");
        assertEquals("SuperAdmin", userType.getName());
    }

    @Test
    public void testGetDescription() {
        UserType userType = new UserType("Admin", "Administrator");
        assertEquals("Administrator", userType.getDescription());
    }

    @Test
    public void testSetDescription() {
        UserType userType = new UserType("Admin", "Administrator");
        userType.setDescription("Super Administrator");
        assertEquals("Super Administrator", userType.getDescription());
    }

    @Test
    public void testGetUsers() {
        User user1 = new User("Doe", "John");
        User user2 = new User("Jane", "Smith");

        List<User> users = new ArrayList<>();
        users.add(user1);
        users.add(user2);

        UserType userType = new UserType("Admin", "Administrator");
        userType.setUsers(users);

        assertEquals(users, userType.getUsers());
    }

    @Test
    public void testSetUsers() {
        User user1 = new User("John", "Doe");
        User user2 = new User("Jane", "Smith");

        List<User> users1 = new ArrayList<>();
        users1.add(user1);

        List<User> users2 = new ArrayList<>();
        users2.add(user2);

        UserType userType = new UserType("Admin", "Administrator");
        userType.setUsers(users1);
        userType.setUsers(users2);

        assertEquals(users2, userType.getUsers());
    }
}