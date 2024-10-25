package com.example.gradecalculator.service;
import com.example.gradecalculator.entities.GradeType;
import com.example.gradecalculator.entities.User;
import com.example.gradecalculator.entities.UserGrade;
import com.example.gradecalculator.entities.UserSubject;
import com.example.gradecalculator.model.GradeTO;
import com.example.gradecalculator.repository.GradeTypeRepository;
import com.example.gradecalculator.repository.UserGradeRepository;
import com.example.gradecalculator.repository.UserRepository;
import com.example.gradecalculator.repository.UserSubjectRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class GradeServiceTest {

    @Mock
    private UserGradeRepository userGradeRepository;

    @Mock
    private UserSubjectRepository userSubjectRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private GradeTypeRepository gradeTypeRepository;

    @InjectMocks
    private GradeService gradeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveGrade_UserSubjectNotFound() {
        // Arrange
        GradeTO gradeTO = new GradeTO();
        gradeTO.setUserSubjectId(1L);
        when(userSubjectRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(RuntimeException.class, () -> gradeService.saveGrade(gradeTO, 1L));
    }

    @Test
    void testSaveGrade_GradeTypeNotFound() {
        // Arrange
        GradeTO gradeTO = new GradeTO();
        gradeTO.setUserSubjectId(1L);
        gradeTO.setGradeTypeId(1L);

        UserSubject userSubject = new UserSubject();
        when(userSubjectRepository.findById(anyLong())).thenReturn(Optional.of(userSubject));
        when(gradeTypeRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(RuntimeException.class, () -> gradeService.saveGrade(gradeTO, 1L));
    }

    @Test
    void testSaveGrade_UserNotFound() {
        // Arrange
        GradeTO gradeTO = new GradeTO();
        gradeTO.setUserSubjectId(1L);
        gradeTO.setGradeTypeId(1L);

        UserSubject userSubject = new UserSubject();
        GradeType gradeType = new GradeType();
        when(userSubjectRepository.findById(anyLong())).thenReturn(Optional.of(userSubject));
        when(gradeTypeRepository.findById(anyLong())).thenReturn(Optional.of(gradeType));
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(RuntimeException.class, () -> gradeService.saveGrade(gradeTO, 1L));
    }

    @Test
    void testSaveGrade_Success() {
        // Arrange
        GradeTO gradeTO = new GradeTO();
        gradeTO.setUserSubjectId(1L);
        gradeTO.setGradeTypeId(1L);
        gradeTO.setGradeValue(95);

        UserSubject userSubject = new UserSubject();
        GradeType gradeType = new GradeType();
        User user = new User();
        when(userSubjectRepository.findById(anyLong())).thenReturn(Optional.of(userSubject));
        when(gradeTypeRepository.findById(anyLong())).thenReturn(Optional.of(gradeType));
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));

        // Act
        gradeService.saveGrade(gradeTO, 1L);

        // Assert
        verify(userGradeRepository, times(1)).save(any(UserGrade.class));
    }
}
