package com.example.gradecalculator.service;

import com.example.gradecalculator.entities.SchoolYear;
import com.example.gradecalculator.entities.Subject;
import com.example.gradecalculator.entities.User;
import com.example.gradecalculator.entities.UserSubject;
import com.example.gradecalculator.mapper.GradeMapper;
import com.example.gradecalculator.mapper.SubjectMapper;
import com.example.gradecalculator.model.SubjectTO;
import com.example.gradecalculator.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SubjectServiceTest {

    private SubjectService subjectService;

    @Mock
    private UserSubjectRepository userSubjectRepository;

    @Mock
    private SubjectRepository subjectRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private SchoolYearRepository schoolYearRepository;

    private SubjectMapper subjectMapper;
    private GradeMapper gradeMapper;
    private Subject subject;
    private Subject secondSubject;
    private User user;
    private UserSubject userSubject;
    private SchoolYear schoolYear;
    @Mock
    private UserGradeRepository userGradeRepository;
    @BeforeEach
    public void setUp() {
        subject = new Subject();
        subject.setId(1L);
        subject.setName("ITT2");
        subject.setDescription("IT Wirtschaft");

        secondSubject = new Subject();
        secondSubject.setId(2L);
        secondSubject.setName("Biology");
        secondSubject.setDescription("Biological Studies");

        user = new User();
        user.setId(1L);

        schoolYear = new SchoolYear();
        schoolYear.setId(1L);
        schoolYear.setStartDate(LocalDate.of(2023, 1, 1));

        userSubject = new UserSubject();
        userSubject.setUser(user);
        userSubject.setSubject(subject);
        userSubject.setSchoolYear(schoolYear);

        subjectMapper = Mappers.getMapper(SubjectMapper.class);
        subjectService = new SubjectService(userSubjectRepository, subjectRepository, userRepository,
                                            schoolYearRepository, subjectMapper, gradeMapper, userGradeRepository);
    }

    @Test
    public void testSubjectToSubjectTO() {
        // Arrange
        SubjectTO subjectTO = subjectMapper.subjectToSubjectTO(subject);

        // Assert
        assertNotNull(subjectTO);
        assertEquals(subject.getId(), subjectTO.getId());
        assertEquals(subject.getName(), subjectTO.getName());
        assertEquals(subject.getDescription(), subjectTO.getDescription());
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
    public void testGetAllSubjects() {
        // Arrange
        Iterable<Subject> subjects = Collections.singletonList(subject);
        when(subjectRepository.findAll()).thenReturn(subjects);

        // Act
        List<SubjectTO> subjectTOs = subjectService.getAllSubjects();

        // Assert
        assertEquals(1, subjectTOs.size());
        assertEquals(subject.getId(), subjectTOs.get(0).getId());
        assertEquals(subject.getName(), subjectTOs.get(0).getName());
    }

    @Test
    public void testGetAllSubjects_SortedAlphabetically() {
        // Arrange
        Iterable<Subject> subjects = Arrays.asList(subject, secondSubject);
        when(subjectRepository.findAll()).thenReturn(subjects);

        // Act
        List<SubjectTO> subjectTOs = subjectService.getAllSubjects();

        // Assert
        assertThat(subjectTOs).hasSize(2);
        assertThat(subjectTOs.get(0).getName()).isEqualTo("Biology");
        assertThat(subjectTOs.get(1).getName()).isEqualTo("ITT2");
        assertEquals(subject.getId(), subjectTOs.get(1).getId());
        assertEquals(subject.getName(), subjectTOs.get(1).getName());
        assertEquals(secondSubject.getId(), subjectTOs.get(0).getId());
        assertEquals(secondSubject.getName(), subjectTOs.get(0).getName());
    }

    @Test
    public void testGetAllSubjects_EmptyList() {
        // Arrange
        when(subjectRepository.findAll()).thenReturn(Collections.emptyList());

        // Act
        List<SubjectTO> subjectTOs = subjectService.getAllSubjects();

        // Assert
        assertThat(subjectTOs).isEmpty();
    }

    @Test
    public void testGetUserSubjectsByYearAndUserId_NotFound() {
        // Arrange
        when(userSubjectRepository.findBySchoolYearAndUserId(anyInt(), anyLong())).thenReturn(Collections.emptySet());

        // Act
        Set<UserSubject> result = subjectService.getUserSubjectsByYearAndUserId(2023, 1L);

        // Assert
        assertThat(result).isEmpty();
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
    public void testSaveSubjectYearSelectedUser_AlreadyExists() {
        // Arrange
        when(schoolYearRepository.findById(anyLong())).thenReturn(Optional.of(schoolYear));
        when(subjectRepository.findById(anyLong())).thenReturn(Optional.of(subject));
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(userSubjectRepository.findByUserId(anyLong())).thenReturn(Collections.singletonList(userSubject));

        // Act
        String error = subjectService.saveSubjectYearSelectedUser(1L, 1L, 1L);

        // Assert
        assertThat(error).isEqualTo("Subject already selected for the given year: ITT2 - null");
        verify(userSubjectRepository, never()).save(any(UserSubject.class));
    }

    @Test
    public void testSaveSubjectYearSelectedUser_SubjectNotFound() {
        // Arrange
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(subjectRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> subjectService.saveSubjectYearSelectedUser(1L, 1L, 1L));
    }

    @Test
    public void testSaveSubjectYearSelectedUser_YearNotFound() {
        // Arrange
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(subjectRepository.findById(anyLong())).thenReturn(Optional.of(subject));
        when(schoolYearRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> subjectService.saveSubjectYearSelectedUser(1L, 1L, 1L));
    }

    @Test
    public void testSaveSubjectYearSelectedUser_UserNotFound() {
        // Arrange
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> subjectService.saveSubjectYearSelectedUser(1L, 1L, 1L));
    }

    @Test
    public void testSaveSubjects_WithErrors() {
        // Arrange
        when(subjectRepository.findById(anyLong())).thenReturn(Optional.of(subject));
        when(schoolYearRepository.findById(anyLong())).thenReturn(Optional.of(schoolYear));
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(userSubjectRepository.findByUserId(anyLong())).thenReturn(Collections.singletonList(userSubject));

        String[] selectedValues = {"1-1"};

        // Act
        List<String> errors = subjectService.saveSubjects(selectedValues, 1L);

        // Assert
        assertThat(errors).isNotEmpty();
        assertThat(errors).contains("Subject already selected for the given year: ITT2 - null");
    }

    @Test
    public void testDeleteSubject() {
        // Arrange
        when(userSubjectRepository.findById(anyLong())).thenReturn(Optional.of(userSubject));
        when(userGradeRepository.existsByUserSubjectIdAndUserId(anyLong(),anyLong())).thenReturn(false);

        // Act
        boolean result = subjectService.deleteSubject(1L, "1");

        // Assert
        assertTrue(result);
        verify(userSubjectRepository, times(1)).delete(any(UserSubject.class));
    }

    @Test
    public void testDeleteSubject_SubjectNotFound() {
        // Arrange
        when(userSubjectRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Act
        boolean result = subjectService.deleteSubject(1L, "1");

        // Assert
        assertThat(result).isFalse();
        verify(userSubjectRepository, never()).delete(any(UserSubject.class));
    }

    @Test
    public void testDeleteSubject_DeleteFails() {
        // Arrange
        when(userSubjectRepository.findById(anyLong())).thenReturn(Optional.of(userSubject));

        // Act
        boolean result = subjectService.deleteSubject(1L, "1");

        // Assert
        assertThat(result).isTrue();
        verify(userSubjectRepository, times(1)).delete(any(UserSubject.class));
    }

    @Test
    public void testDeleteSubject_UserIdMismatch() {
        // Arrange
        userSubject.getUser().setId(2L);
        when(userSubjectRepository.findById(anyLong())).thenReturn(Optional.of(userSubject));

        // Act
        boolean result = subjectService.deleteSubject(1L, "1");

        // Assert
        assertThat(result).isFalse();
        verify(userSubjectRepository, never()).delete(any(UserSubject.class));
    }
}