package com.example.gradecalculator.entities;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;


import static org.junit.Assert.assertEquals;

public class UserTest {

    private User user;

    @BeforeEach
    public void setUp() {
        user = new User();
    }

    @Test
    public void testGetId() {
        user.setId(1L);
        assertEquals(1L, user.getId().longValue());
    }

    @Test
    public void testGetFirstName() {
        user.setFirstName("John");
        assertEquals("John", user.getFirstName());
    }

    @Test
    public void testGetEmail() {
        user.setEmail("john@example.com");
        assertEquals("john@example.com", user.getEmail());
    }
}
