package com.example.gradecalculator.entities;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.assertEquals;

public class GradeSystemTest {

    private GradeSystem gradeSystem;
    private List<GradeType> gradeTypes;
    private List<SchoolYear> schoolYears;

    @Before
    public void setUp() {
        gradeSystem = new GradeSystem();
        gradeSystem.setId(1L);
        gradeSystem.setName("Grading Percentage");
        gradeSystem.setDescription("from 0 to 100 percent");

        gradeTypes = new ArrayList<>();

        var gradeType1 = new GradeType();
        gradeType1.setId(1L);
        gradeType1.setDescription("A");
        gradeType1.setGradeSystem(gradeSystem);
        gradeTypes.add(gradeType1);

        var gradeType2 = new GradeType();
        gradeType2.setId(2L);
        gradeType2.setDescription("A-");
        gradeType2.setGradeSystem(gradeSystem);
        gradeTypes.add(gradeType2);

        gradeSystem.setGradeTypes(gradeTypes);

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

}
