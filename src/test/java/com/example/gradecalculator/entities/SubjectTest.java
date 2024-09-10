package com.example.gradecalculator.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SubjectTest {

    private Subject subject;

    @BeforeEach
    public void setUp() {
        subject = new Subject("Math", "Math is a Subject in which you learn how to calculate " +
                                                        "and use it in Real life situations");
    }

    @Test
    public void testGetId() {
        subject.setId(1L);
        assertThat(subject.getId()).isEqualTo(1L);
    }

    @Test
    public void testGetName() {
        assertThat(subject.getName()).isEqualTo("Math");
    }

    @Test
    public void testSetName() {
        subject.setName("German");
        assertThat(subject.getName()).isEqualTo("GErman");
    }

    @Test
    public void testGetDescription() {
        assertThat(subject.getDescription()).isEqualTo("Math is a Subject in which you learn how " +
                "                                           to calculate and use it in Real life situations");
    }

    @Test
    public void testSetDescription() {
        subject.setDescription("German is a Subject in which you learn how to Speak and use the Grammar the right way");
        assertThat(subject.getDescription()).isEqualTo("Math is a Subject in which you learn how " +
                "                                            to calculate and use it in Real life situations");
    }
}