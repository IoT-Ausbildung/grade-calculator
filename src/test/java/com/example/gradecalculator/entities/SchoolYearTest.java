package com.example.gradecalculator.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

public class SchoolYearTest {

    private SchoolYear schoolYear;

    @BeforeEach
    public void setUp() {
        schoolYear = new SchoolYear("2022-2023", LocalDate.of(2022, 9, 1), LocalDate.of(2023, 6, 30));
    }

    @Test
    public void testGetId() {
        schoolYear.setId(1L);
        assertThat(schoolYear.getId()).isEqualTo(1L);
    }

    @Test
    public void testGetName() {
        assertThat(schoolYear.getName()).isEqualTo("2022-2023");
    }

    @Test
    public void testSetName() {
        schoolYear.setName("2023-2024");
        assertThat(schoolYear.getName()).isEqualTo("2023-2024");
    }

    @Test
    public void testGetStartDate() {
        assertThat(schoolYear.getStartDate()).isEqualTo(LocalDate.of(2022, 9, 1));
    }

    @Test
    public void testSetStartDate() {
        schoolYear.setStartDate(LocalDate.of(2023, 8, 1));
        assertThat(schoolYear.getStartDate()).isEqualTo(LocalDate.of(2023, 8, 1));
    }

    @Test
    public void testGetEndDate() {
        assertThat(schoolYear.getEndDate()).isEqualTo(LocalDate.of(2023, 6, 30));
    }

    @Test
    public void testSetEndDate() {
        schoolYear.setEndDate(LocalDate.of(2024, 6, 30));
        assertThat(schoolYear.getEndDate()).isEqualTo(LocalDate.of(2024, 6, 30));
    }

    @Test
    public void testNoArgsConstructor() {
        SchoolYear emptySchoolYear = new SchoolYear();
        assertThat(emptySchoolYear.getId()).isNull();
        assertThat(emptySchoolYear.getName()).isNull();
        assertThat(emptySchoolYear.getStartDate()).isNull();
        assertThat(emptySchoolYear.getEndDate()).isNull();
    }

    @Test
    public void testOverloadedConstructorWithIdAndName() {
        SchoolYear schoolYearWithIdAndName = new SchoolYear(2L, "2021-2022");
        assertThat(schoolYearWithIdAndName.getId()).isEqualTo(2L);
        assertThat(schoolYearWithIdAndName.getName()).isEqualTo("2021-2022");
    }


}