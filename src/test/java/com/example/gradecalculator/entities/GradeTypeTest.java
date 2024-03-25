package com.example.gradecalculator.entities;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class GradeTypeTest {

    @Test
    public void testGetWeightagePercentage() {
        GradeType gradeType = new GradeType();
        gradeType.setWeightage(0.50);

        String expectedPercentage = "50%";

        String actualPercentage = gradeType.getWeightagePercentage();

        Assertions.assertEquals(expectedPercentage, actualPercentage);
    }

    @Test
    public void testGettersAndSetters() {

        GradeType gradeType = new GradeType();

        gradeType.setId(1L);
        gradeType.setName("Grade Type 1");
        gradeType.setDescription("Description 1");
        gradeType.setWeightage(0.50);

        Assertions.assertEquals(1L, gradeType.getId());
        Assertions.assertEquals("Grade Type 1", gradeType.getName());
        Assertions.assertEquals("Description 1", gradeType.getDescription());
        Assertions.assertEquals(0.50, gradeType.getWeightage().doubleValue());
    }
}