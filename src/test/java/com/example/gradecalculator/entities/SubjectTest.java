package com.example.gradecalculator.entities;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SubjectTest {

    @Test
    public void testGettersAndSetters() {

        Subject subject = new Subject();


        subject.setId(1L);
        subject.setName("Subject 1");
        subject.setDescription("Description 1");
        subject.setCreditValue(3);


        Assertions.assertEquals(1L, subject.getId());
        Assertions.assertEquals("Subject 1", subject.getName());
        Assertions.assertEquals("Description 1", subject.getDescription());
        Assertions.assertEquals(3, subject.getCreditValue());
    }
}