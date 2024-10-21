package com.example.gradecalculator.entities;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GradeTypeTest {


    private GradeType gradeType;

    @BeforeEach
    public void setUp() {
        gradeType = new GradeType();
    }

    @Test
    public void testGetWeightagePercentage() {
        gradeType.setWeightage(0.50);
        String expectedPercentage = "50%";
        String actualPercentage = gradeType.getWeightagePercentage();

        Assertions.assertEquals(expectedPercentage, actualPercentage);
    }

    @Test
    public void testGettersAndSetters() {
        gradeType.setId(1L);
        gradeType.setName("Grade Type 1");
        gradeType.setWeightage(0.50);

        Assertions.assertEquals(1L, gradeType.getId());
        Assertions.assertEquals("Grade Type 1", gradeType.getName());
        Assertions.assertEquals(0.50, gradeType.getWeightage().doubleValue());
    }
}