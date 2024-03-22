package com.example.gradecalculator.entities;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class GradeSystemTest {

    @Test
    public void testGettersAndSetters() {

        GradeSystem gradeSystem = new GradeSystem();
        gradeSystem.setId(1L);
        gradeSystem.setName("Grade System 1");
        gradeSystem.setDescription("Description 1");
        Assertions.assertEquals(1L, gradeSystem.getId());
        Assertions.assertEquals("Grade System 1", gradeSystem.getName());
        Assertions.assertEquals("Description 1", gradeSystem.getDescription());
    }
}
