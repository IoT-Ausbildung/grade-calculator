package com.example.gradecalculator.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class UserTypeTest {

    private UserType userType;

    @BeforeEach
    public void setUp() {
        userType = new UserType();
    }

    @Test
    public void testGetId() {
        userType.setId(1L);
        assertThat(userType.getId()).isEqualTo(1L);
    }

    @Test
    public void testGetName() {
        userType.setName("Admin");
        assertThat(userType.getName()).isEqualTo("Admin");
    }

    @Test
    public void testSetName() {
        userType.setName("Super Admin");
        assertThat(userType.getName()).isEqualTo("Super Admin");
    }

    @Test
    public void testGetDescription() {
        userType.setDescription("Administrator user type");
        assertThat(userType.getDescription()).isEqualTo("Administrator user type");
    }

    @Test
    public void testSetDescription() {
        userType.setDescription("Super user type");
        assertThat(userType.getDescription()).isEqualTo("Super user type");
    }

    @Test
    public void testGetUsers() {
        List<User> users = new ArrayList<>();
        userType.setUsers(users);
        assertThat(userType.getUsers()).isEqualTo(users);
    }

    @Test
    public void testSetUsers() {
        List<User> users1 = new ArrayList<>();
        users1.add(new User());
        List<User> users2 = new ArrayList<>();
        users2.add(new User());
        userType.setUsers(users1);
        userType.setUsers(users2);
        assertThat(userType.getUsers()).isEqualTo(users2);
    }
}