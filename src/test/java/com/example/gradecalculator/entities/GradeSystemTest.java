package com.example.gradecalculator.entities;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
public class GradeSystemTest {
        private GradeSystem gradeSystem;
        private List<GradeType> gradeTypes;
        private List<SchoolYear> schoolYears;

        @Before
        public void setUp() {
            gradeSystem = new GradeSystem("Grade System");
            gradeSystem.setId(1L);
            gradeSystem.setName("Grading Percentage");
            gradeSystem.setDescription("from 0 to 100 percent");

            gradeTypes = new ArrayList<>();
            gradeTypes.add(new GradeType(1L, "A", gradeSystem));
            gradeTypes.add(new GradeType(2L, "A-", gradeSystem));
            gradeSystem.setGradeTypes(gradeTypes);

            schoolYears = new ArrayList<>();
            schoolYears.add(new SchoolYear(1L, "2021-2022", gradeSystem));
            schoolYears.add(new SchoolYear(2L, "2022-2023", gradeSystem));
            gradeSystem.setSchoolYears(schoolYears);
        }

        @Test
        public void testGetId() {
            assertEquals(1L, gradeSystem.getId().longValue());
        }

        @Test
        public void testGetName() {
            assertEquals("Grading Percentage", gradeSystem.getName());
        }

        @Test
        public void testGetDescription() {
            assertEquals("from 0 to 100 percent", gradeSystem.getDescription());
        }

        @Test
        public void testGetGradeTypes() {
            assertEquals(gradeTypes, gradeSystem.getGradeTypes());
        }

        @Test
        public void testGetSchoolYears() {
            assertEquals(schoolYears, gradeSystem.getSchoolYears());
        }

        @Test
        public void testSetId() {
            gradeSystem.setId(2L);
            assertEquals(2L, gradeSystem.getId().longValue());
        }

        @Test
        public void testSetName() {
            gradeSystem.setName("Letter Grading and Variations");
            assertEquals("Letter Grading and Variations", gradeSystem.getName());
        }

        @Test
        public void testSetDescription() {
            gradeSystem.setDescription("from A grade to F grade ");
            assertEquals("from A grade to F grade ", gradeSystem.getDescription());
        }

        @Test
        public void testSetGradeTypes() {
            List<GradeType> newGradeTypes = new ArrayList<>();
            newGradeTypes.add(new GradeType(3L, "B+", gradeSystem));
            gradeSystem.setGradeTypes(newGradeTypes);
            assertEquals(newGradeTypes, gradeSystem.getGradeTypes());
        }

        @Test
        public void testSetSchoolYears() {
            List<SchoolYear> newSchoolYears = new ArrayList<>();
            newSchoolYears.add(new SchoolYear(3L, "2023-2024", gradeSystem));
            gradeSystem.setSchoolYears(newSchoolYears);
            assertEquals(newSchoolYears, gradeSystem.getSchoolYears());
        }
    }
