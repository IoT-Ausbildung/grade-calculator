package com.example.gradecalculator.entities;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SchoolYearTest {

    @Test
    public void testGetId() {
        SchoolYear schoolYear = new SchoolYear("2022-2023", LocalDate.of(2022, 9, 1), LocalDate.of(2023, 6, 30));
        schoolYear.setId(1L);
        Assertions.assertEquals(1L, schoolYear.getId());
    }

    @Test
    public void testGetName() {
        SchoolYear schoolYear = new SchoolYear("2022-2023", LocalDate.of(2022, 9, 1), LocalDate.of(2023, 6, 30));
        Assertions.assertEquals("2022-2023", schoolYear.getName());
    }

    @Test
    public void testSetName() {
        SchoolYear schoolYear = new SchoolYear("2022-2023", LocalDate.of(2022, 9, 1), LocalDate.of(2023, 6, 30));
        schoolYear.setName("2023-2024");
        Assertions.assertEquals("2023-2024", schoolYear.getName());
    }

    @Test
    public void testGetStartDate() {
        SchoolYear schoolYear = new SchoolYear("2022-2023", LocalDate.of(2022, 9, 1), LocalDate.of(2023, 6, 30));
        Assertions.assertEquals(LocalDate.of(2022, 9, 1), schoolYear.getStartDate());
    }

    @Test
    public void testSetStartDate() {
        SchoolYear schoolYear = new SchoolYear("2022-2023", LocalDate.of(2022, 9, 1), LocalDate.of(2023, 6, 30));
        schoolYear.setStartDate(LocalDate.of(2023, 8, 1));
        Assertions.assertEquals(LocalDate.of(2023, 8, 1), schoolYear.getStartDate());
    }

    @Test
    public void testGetEndDate() {
        SchoolYear schoolYear = new SchoolYear("2022-2023", LocalDate.of(2022, 9, 1), LocalDate.of(2023, 6, 30));
        Assertions.assertEquals(LocalDate.of(2023, 6, 30), schoolYear.getEndDate());
    }

    @Test
    public void testSetEndDate() {
        SchoolYear schoolYear = new SchoolYear("2022-2023", LocalDate.of(2022, 9, 1), LocalDate.of(2023, 6, 30));
        schoolYear.setEndDate(LocalDate.of(2024, 6, 30));
        Assertions.assertEquals(LocalDate.of(2024, 6, 30), schoolYear.getEndDate());
    }

    @Test
    public void testGetGradeSystem() {
        GradeSystem gradeSystem = new GradeSystem(1L, "Grade System");
        SchoolYear schoolYear = new SchoolYear("2022-2023", LocalDate.of(2022, 9, 1), LocalDate.of(2023, 6, 30));

    }

    @Test
    public void testSetGradeSystem() {
        GradeSystem gradeSystem1 = new GradeSystem(1L, "Grade System 1");
        GradeSystem gradeSystem2 = new GradeSystem(2L, "Grade System 2");
        SchoolYear schoolYear = new SchoolYear("2022-2023", LocalDate.of(2022, 9, 1), LocalDate.of(2023, 6, 30));
    }

    @Test
    public void testGetSubjects() {
        List<Subject> subjects = new ArrayList<>();
        subjects.add(new Subject("Math", "Mathematics"));
        subjects.add(new Subject("Science", "Science subject"));
        SchoolYear schoolYear = new SchoolYear("2022-2023", LocalDate.of(2022, 9, 1), LocalDate.of(2023, 6, 30));
        schoolYear.setSubjects(subjects);
    }
}

