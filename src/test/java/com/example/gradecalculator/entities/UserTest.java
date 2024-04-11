package com.example.gradecalculator.entities;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UserTest {

    @Test
    public void testGetId() {
        User user = new User();
        user.setId(1L);
        assertEquals(1L, user.getId().longValue());
    }

    @Test
    public void testGetFirstName() {
        User user = new User();
        user.setFirstName("John");
        assertEquals("John", user.getFirstName());
    }

    @Test
    public void testGetEmail() {
        User user = new User();
        user.setEmail("john@example.com");
        assertEquals("john@example.com", user.getEmail());
    }
}
