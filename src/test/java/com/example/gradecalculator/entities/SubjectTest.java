package com.example.gradecalculator.entities;

import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;

public class SubjectTest {

    @Test
    public void testGetId() {
        Subject subject = new Subject("Math", "Mathematics");
        subject.setId(1L);
        assertEquals(1L, subject.getId().longValue());
    }

    @Test
    public void testGetName() {
        Subject subject = new Subject("Math", "Mathematics");
        assertEquals("Math", subject.getName());
    }

    @Test
    public void testSetName() {
        Subject subject = new Subject("Math", "Mathematics");
        subject.setName("Science");
        assertEquals("Science", subject.getName());
    }

    @Test
    public void testGetDescription() {
        Subject subject = new Subject("Math", "Mathematics");
        assertEquals("Mathematics", subject.getDescription());
    }

    @Test
    public void testSetDescription() {
        Subject subject = new Subject("Math", "Mathematics");
        subject.setDescription("Algebra");
        assertEquals("Algebra", subject.getDescription());
    }

    @Test
    public void testGetSchoolYear() {
        SchoolYear schoolYear = new SchoolYear("2022-2023", LocalDate.of(2022, 9, 1), LocalDate.of(2023, 6, 30), new GradeSystem(1L, "Grade System"));
        Subject subject = new Subject("Math", "Mathematics");
        subject.setSchoolYear(schoolYear);
        assertEquals(schoolYear, subject.getSchoolYear());
    }

    @Test
    public void testSetSchoolYear() {
        SchoolYear schoolYear1 = new SchoolYear("2022-2023", LocalDate.of(2022, 9, 1), LocalDate.of(2023, 6, 30), new GradeSystem(1L, "Grade System"));
        SchoolYear schoolYear2 = new SchoolYear("2023-2024", LocalDate.of(2023, 9, 1), LocalDate.of(2024, 6, 30), new GradeSystem(2L, "Grade System"));
        Subject subject = new Subject("Math", "Mathematics");
        subject.setSchoolYear(schoolYear1);
        subject.setSchoolYear(schoolYear2);
        assertEquals(schoolYear2, subject.getSchoolYear());
    }

    @Test
    public void testGetCreditValue() {
        Subject subject = new Subject("Math", "Mathematics");
        subject.setCreditValue(3);
        assertEquals(3, subject.getCreditValue());
    }

    @Test
    public void testSetCreditValue() {
        Subject subject = new Subject("Math", "Mathematics");
        subject.setCreditValue(4);
        assertEquals(4, subject.getCreditValue());
    }
}