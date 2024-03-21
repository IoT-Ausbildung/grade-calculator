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
    public void testGetName() {
        User user = new User("John Doe", "john@example.com", "Hello");
        assertEquals("John Doe", user.getName());
    }

    @Test
    public void testGetEmail() {
        User user = new User("John Doe", "john@example.com", "Hello");
        assertEquals("john@example.com", user.getEmail());
    }

    @Test
    public void testGetMessage() {
        User user = new User("John Doe", "john@example.com", "Hello");
        assertEquals("Hello", user.getMessage());
    }

    @Test
    public void testSetId() {
        User user = new User();
        user.setId(2L);
        assertEquals(2L, user.getId().longValue());
    }

    @Test
    public void testSetName() {
        User user = new User();
        user.setName("Jane Smith");
        assertEquals("Jane Smith", user.getName());
    }

    @Test
    public void testSetEmail() {
        User user = new User();
        user.setEmail("jane@example.com");
        assertEquals("jane@example.com", user.getEmail());
    }

    @Test
    public void testSetMessage() {
        User user = new User();
        user.setMessage("Goodbye");
        assertEquals("Goodbye", user.getMessage());
    }
}
