package com.example.gradecalculator.service;

import com.example.gradecalculator.entities.SchoolYear;
import com.example.gradecalculator.entities.Subject;
import com.example.gradecalculator.entities.User;
import com.example.gradecalculator.entities.UserSubject;
import com.example.gradecalculator.model.SubjectTO;
import com.example.gradecalculator.repository.SchoolYearRepository;
import com.example.gradecalculator.repository.SubjectRepository;
import com.example.gradecalculator.repository.UserRepository;
import com.example.gradecalculator.repository.UserSubjectRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SubjectServiceTest {

    @InjectMocks
    private SubjectService subjectService;

    @Mock
    private UserSubjectRepository userSubjectRepository;

    @Mock
    private SubjectRepository subjectRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private SchoolYearRepository schoolYearRepository;

    private Subject subject;
    private User user;
    private UserSubject userSubject;
    private SchoolYear schoolYear;

    @BeforeEach
    public void setUp() {
        subject = new Subject();
        subject.setId(1L);
        subject.setName("ITT2");
        subject.setDescription("IT Wirtschaft");

        user = new User();
        user.setId(1L);

        schoolYear = new SchoolYear();
        schoolYear.setId(1L);
        schoolYear.setStartDate(LocalDate.of(2023, 1, 1));

        userSubject = new UserSubject();
        userSubject.setUser(user);
        userSubject.setSubject(subject);
        userSubject.setSchoolYear(schoolYear);
    }

    @Test
    public void testGetAllSubjects() {
        // Arrange
        Iterable<Subject> subjects = Collections.singletonList(subject);
        when(subjectRepository.findAll()).thenReturn(subjects);

        // Act
        List<SubjectTO> subjectTOs = subjectService.getAllSubjects();

        // Assert
        assertFalse(subjectTOs.isEmpty());
        assertEquals(1, subjectTOs.size());
    }

    @Test
    public void testGetUserSubjectsByYearAndUserId() {
        // Arrange
        Set<UserSubject> userSubjects = new HashSet<>(Collections.singletonList(userSubject));
        when(userSubjectRepository.findBySchoolYearAndUserId(anyInt(), anyLong())).thenReturn(userSubjects);

        // Act
        Set<UserSubject> result = subjectService.getUserSubjectsByYearAndUserId(2023, 1L);

        // Assert
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
    }

    @Test
    public void testSelectSubjectForYear() {
        // Act
        subjectService.selectSubjectForYear(2023, "ITT2");
        Set<String> selectedSubjects = subjectService.getSelectedSubjectsForYear(2023);

        // Assert
        assertNotNull(selectedSubjects);
        assertTrue(selectedSubjects.contains("ITT2"));
    }

    @Test
    public void testRemoveSubjectForYear() {
        // Arrange
        subjectService.selectSubjectForYear(2023, "ITT2");

        // Act
        subjectService.removeSubjectForYear(2023, "ITT2");
        Set<String> selectedSubjects = subjectService.getSelectedSubjectsForYear(2023);

        // Assert
        assertTrue(selectedSubjects.isEmpty());
    }

    @Test
    public void testSaveSubjectYearSelectedUser() {
        // Arrange
        when(schoolYearRepository.findById(anyLong())).thenReturn(Optional.of(schoolYear));
        when(subjectRepository.findById(anyLong())).thenReturn(Optional.of(subject));
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(userSubjectRepository.findByUserId(anyLong())).thenReturn(Collections.emptyList());

        // Act
        subjectService.saveSubjectYearSelectedUser(1L, 1L, 1L);

        // Assert
        verify(userSubjectRepository, times(1)).save(any(UserSubject.class));
    }

    @Test
    public void testDeleteSubject() {
        // Arrange
        when(userSubjectRepository.findById(anyLong())).thenReturn(Optional.of(userSubject));

        // Act
        boolean result = subjectService.deleteSubject(1L, "1");

        // Assert
        assertTrue(result);
        verify(userSubjectRepository, times(1)).delete(any(UserSubject.class));
    }

    @Test
    public void testDeleteSubjectNotOwner() {
        // Arrange
        userSubject.getUser().setId(2L);
        when(userSubjectRepository.findById(anyLong())).thenReturn(Optional.of(userSubject));

        // Act
        boolean result = subjectService.deleteSubject(1L, "1");

        // Assert
        assertFalse(result);
        verify(userSubjectRepository, times(0)).delete(any(UserSubject.class));
    }
}