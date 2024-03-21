package com.example.gradecalculator.entities;

import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class SchoolYearTest {

    @Test
    public void testGetId() {
        SchoolYear schoolYear = new SchoolYear("2022-2023", LocalDate.of(2022, 9, 1), LocalDate.of(2023, 6, 30), new GradeSystem(1L, "Grade System"));
        schoolYear.setId(1L);
        assertEquals(1L, schoolYear.getId().longValue());
    }

    @Test
    public void testGetName() {
        SchoolYear schoolYear = new SchoolYear("2022-2023", LocalDate.of(2022, 9, 1), LocalDate.of(2023, 6, 30), new GradeSystem(1L, "Grade System"));
        assertEquals("2022-2023", schoolYear.getName());
    }

    @Test
    public void testSetName() {
        SchoolYear schoolYear = new SchoolYear("2022-2023", LocalDate.of(2022, 9, 1), LocalDate.of(2023, 6, 30), new GradeSystem(1L, "Grade System"));
        schoolYear.setName("2023-2024");
        assertEquals("2023-2024", schoolYear.getName());
    }

    @Test
    public void testGetStartDate() {
        SchoolYear schoolYear = new SchoolYear("2022-2023", LocalDate.of(2022, 9, 1), LocalDate.of(2023, 6, 30), new GradeSystem(1L, "Grade System"));
        assertEquals(LocalDate.of(2022, 9, 1), schoolYear.getStartDate());
    }

    @Test
    public void testSetStartDate() {
        SchoolYear schoolYear = new SchoolYear("2022-2023", LocalDate.of(2022, 9, 1), LocalDate.of(2023, 6, 30), new GradeSystem(1L, "Grade System"));
        schoolYear.setStartDate(LocalDate.of(2023, 8, 1));
        assertEquals(LocalDate.of(2023, 8, 1), schoolYear.getStartDate());
    }

    @Test
    public void testGetEndDate() {
        SchoolYear schoolYear = new SchoolYear("2022-2023", LocalDate.of(2022, 9, 1), LocalDate.of(2023, 6, 30), new GradeSystem(1L, "Grade System"));
        assertEquals(LocalDate.of(2023, 6, 30), schoolYear.getEndDate());
    }

    @Test
    public void testSetEndDate() {
        SchoolYear schoolYear = new SchoolYear("2022-2023", LocalDate.of(2022, 9, 1), LocalDate.of(2023, 6, 30), new GradeSystem(1L, "Grade System"));
        schoolYear.setEndDate(LocalDate.of(2024, 6, 30));
        assertEquals(LocalDate.of(2024, 6, 30), schoolYear.getEndDate());
    }

    @Test
    public void testGetGradeSystem() {
        GradeSystem gradeSystem = new GradeSystem(1L, "Grade System");
        SchoolYear schoolYear = new SchoolYear("2022-2023", LocalDate.of(2022, 9, 1), LocalDate.of(2023, 6, 30), gradeSystem);
        assertEquals(gradeSystem, schoolYear.getGradeSystem());
    }

    @Test
    public void testSetGradeSystem() {
        GradeSystem gradeSystem1 = new GradeSystem(1L, "Grade System 1");
        GradeSystem gradeSystem2 = new GradeSystem(2L, "Grade System 2");
        SchoolYear schoolYear = new SchoolYear("2022-2023", LocalDate.of(2022, 9, 1), LocalDate.of(2023, 6, 30), gradeSystem1);
        schoolYear.setGradeSystem(gradeSystem2);
        assertEquals(gradeSystem2, schoolYear.getGradeSystem());
    }

    @Test
    public void testGetSubjects() {
        List<Subject> subjects = new ArrayList<>();
        subjects.add(new Subject("Math"));
        subjects.add(new Subject("Science"));
        SchoolYear schoolYear = new SchoolYear("2022-2023", LocalDate.of(2022, 9, 1), LocalDate.of(2023, 6, 30), new GradeSystem(1L, "Grade System"));
        schoolYear.setSubjects(subjects);
        assertEquals(subjects, schoolYear.getSubjects());
    }

    @Test
    public void testSetSubjects() {
        List<Subject> subjects1 = new ArrayList<>();
        subjects1.add(new Subject("Math"));
        subjects1.add(new Subject("Science"));

        List<Subject> subjects2 = new ArrayList<>();
        subjects2.add(new Subject("English"));
        subjects2.add(new Subject("History"));

        SchoolYear schoolYear = new SchoolYear("2022-2023", LocalDate.of(2022, 9, 1), LocalDate.of(2023, 6, 30), new GradeSystem(1L, "Grade System"));
        schoolYear.setSubjects(subjects1);
        schoolYear.setSubjects(subjects2);
        assertEquals(subjects2, schoolYear.getSubjects());
    }
}
