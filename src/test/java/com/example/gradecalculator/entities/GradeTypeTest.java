package com.example.gradecalculator.entities;

import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

public class GradeTypeTest {

    @Test
    public void testGetDescription() {
        GradeType gradeType = new GradeType("Grade Type 1", "Description", BigDecimal.valueOf(0.5));
        assertEquals("Description", gradeType.getDescription());
    }

    @Test
    public void testGetWeightage() {
        GradeType gradeType = new GradeType("Grade Type 1", "Description", BigDecimal.valueOf(0.5));
        assertEquals(BigDecimal.valueOf(0.5), gradeType.getWeightage());
    }

    @Test
    public void testGetGradeSystem() {
        GradeSystem gradeSystem = new GradeSystem(1L, "Grade System");
        GradeType gradeType = new GradeType("Grade Type 1", "Description", BigDecimal.valueOf(0.5));
        gradeType.setGradeSystem(gradeSystem);
        assertEquals(gradeSystem, gradeType.getGradeSystem());
    }

    @Test
    public void testGetId() {
        GradeType gradeType = new GradeType("Grade Type 1", "Description", BigDecimal.valueOf(0.5));
        gradeType.setId(1L);
        assertEquals(1L, gradeType.getId().longValue());
    }

    @Test
    public void testGetName() {
        GradeType gradeType = new GradeType("Grade Type 1", "Description", BigDecimal.valueOf(0.5));
        assertEquals("Grade Type 1", gradeType.getName());
    }

    @Test
    public void testGetWeightagePercentage() {
        GradeType gradeType = new GradeType("Grade Type 1", "Description", BigDecimal.valueOf(0.5));
        assertEquals("50%", gradeType.getWeightagePercentage());
    }

    @Test
    public void testSetName() {
        GradeType gradeType = new GradeType("Grade Type 1", "Description", BigDecimal.valueOf(0.5));
        gradeType.setName("Grade Type 2");
        assertEquals("Grade Type 2", gradeType.getName());
    }

    @Test
    public void testSetDescription() {
        GradeType gradeType = new GradeType("Grade Type 1", "Description", BigDecimal.valueOf(0.5));
        gradeType.setDescription("New Description");
        assertEquals("New Description", gradeType.getDescription());
    }

    @Test
    public void testSetWeightage() {
        GradeType gradeType = new GradeType("Grade Type 1", "Description", BigDecimal.valueOf(0.5));
        gradeType.setWeightage(1);
        assertEquals(BigDecimal.valueOf(1), gradeType.getWeightage());
    }

    @Test
    public void testSetGradeSystem() {
        GradeSystem gradeSystem1 = new GradeSystem(1L, "Grade System 1");
        GradeSystem gradeSystem2 = new GradeSystem(2L, "Grade System 2");
        GradeType gradeType = new GradeType("Grade Type 1", "Description", BigDecimal.valueOf(0.5));
        gradeType.setGradeSystem(gradeSystem1);
        gradeType.setGradeSystem(gradeSystem2);
        assertEquals(gradeSystem2, gradeType.getGradeSystem());
    }
}