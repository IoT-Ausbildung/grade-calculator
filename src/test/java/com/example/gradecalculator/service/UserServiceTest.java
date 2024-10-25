package com.example.gradecalculator.service;

import com.example.gradecalculator.entities.User;
import com.example.gradecalculator.model.UserDetailsImpl;
import com.example.gradecalculator.model.UserEditTO;
import com.example.gradecalculator.repository.UserRepository;
import com.example.gradecalculator.repository.UserSubjectRepository;
import com.example.gradecalculator.repository.UserTypeRepository;
import com.example.gradecalculator.model.UserSignUpTO;
import com.example.gradecalculator.mapper.UserRegistrationMapper;
import com.example.gradecalculator.entities.UserType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserRegistrationMapper userRegistrationMapper;

    @Mock
    private UserTypeRepository userTypeRepository;

    @Mock
    private UserSubjectRepository userSubjectRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private BindingResult bindingResult;

    @Mock
    private Authentication authentication;

    @Test
    public void testCreateUser() {
        // Arrange
        String rawPassword = "plainPassword";
        String encodedPassword = "encodedPassword";
        UserSignUpTO registration = new UserSignUpTO();
        registration.setPassword(rawPassword);
        registration.setUserType(1L);

        UserType userType = new UserType();
        userType.setId(1L);

        User user = new User();
        user.setEncodedPassword(rawPassword);

        when(passwordEncoder.encode(rawPassword)).thenReturn(encodedPassword);
        when(userRegistrationMapper.TOToEntity(any(UserSignUpTO.class))).thenReturn(user);
        when(userTypeRepository.findById(registration.getUserType())).thenReturn(java.util.Optional.of(userType));

        // Act
        User createdUser = userService.createUser(registration);

        // Assert
        assertNotNull(createdUser);
        assertEquals(encodedPassword, createdUser.getEncodedPassword());
        assertEquals(userType, createdUser.getUserType());

        verify(userRepository, times(1)).save(createdUser);
    }

    @Test
    public void createUser_WhenUsertypeNotFound_ShouldThrowException() {
        //Arrange
        UserSignUpTO registration = new UserSignUpTO();
        registration.setUserType(1L);

        when(userRegistrationMapper.TOToEntity(any(UserSignUpTO.class))).thenReturn(new User());
        when(userTypeRepository.findById(registration.getUserType())).thenReturn(java.util.Optional.empty());


        //Act
        //Assert
        assertThrows(IllegalArgumentException.class, () -> userService.createUser(registration));
    }

    @Test
    public void testEditProfile() {
        //Arrange
        User user = new User();
        user.setId(1L);
        user.setFirstName("oldFirstName");
        user.setLastName("oldLastName");

        UserEditTO editProfile = new UserEditTO();
        editProfile.setFirstName("firstName");
        editProfile.setLastName("lastName");

        when(userRepository.findById(user.getId())).thenReturn(java.util.Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);

        //Act
        User editUser = userService.editProfile(1L, editProfile);

        //Assert
        assertNotNull(editUser);
        assertEquals(editProfile.getFirstName(), editUser.getFirstName());
        assertEquals(editProfile.getLastName(), editUser.getLastName());

        verify(userRepository, times(1)).findById(user.getId());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    public void testEditPasswordService_PasswordsMatchAndDifferent() {
        // Arrange
        User user = new User();
        String oldPassword = "oldPassword";
        String newPassword = "newPassword";
        String encodedOldPassword = "encodedOldPassword";

        user.setEncodedPassword(encodedOldPassword);
        when(passwordEncoder.matches(oldPassword, encodedOldPassword)).thenReturn(true);
        when(passwordEncoder.encode(newPassword)).thenReturn("encodedNewPassword");

        // Act
        List<String> errors = userService.editPasswordService(oldPassword, newPassword, user);

        // Assert
        assertTrue(errors.isEmpty());
        assertEquals("encodedNewPassword", user.getEncodedPassword());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    public void testEditPasswordService_OldAndNewPasswordsSame() {
        // Arrange
        User user = new User();
        String oldPassword = "oldPassword";
        String newPassword = "oldPassword";
        String encodedOldPassword = "encodedOldPassword";

        user.setEncodedPassword(encodedOldPassword);
        when(passwordEncoder.matches(oldPassword, encodedOldPassword)).thenReturn(true);

        // Act
        List<String> errors = userService.editPasswordService(oldPassword, newPassword, user);

        // Assert
        assertFalse(errors.isEmpty());
        assertEquals("You are currently using this password.", errors.get(0));
        verify(userRepository, never()).save(user);
    }

    @Test
    public void testEditPasswordService_InvalidOldPassword() {
        // Arrange
        User user = new User();
        String oldPassword = "oldPassword";
        String newPassword = "newPassword";
        String encodedOldPassword = "encodedOldPassword";

        user.setEncodedPassword(encodedOldPassword);
        when(passwordEncoder.matches(oldPassword, encodedOldPassword)).thenReturn(false);

        // Act
        List<String> errors = userService.editPasswordService(oldPassword, newPassword, user);

        // Assert
        assertFalse(errors.isEmpty());
        assertEquals("Passwords do not match.", errors.get(0));
        verify(userRepository, never()).save(user);
    }

    @Test
    public void testValidateUserSignUpTO_EmailAlreadyInUse() {
        // Arrange
        UserSignUpTO registration = new UserSignUpTO();
        registration.setEmail("test@example.com");
        registration.setUserName("username");

        when(userRepository.existsByEmail(registration.getEmail())).thenReturn(true);

        // Act
        ArrayList<String> errors = userService.validateUserSignUpTO(registration, bindingResult);

        // Assert
        assertFalse(errors.isEmpty());
        assertEquals("Email is already in use.", errors.get(0));
    }

    @Test
    public void testValidateUserSignUpTO_UsernameAlreadyInUse() {
        // Arrange
        UserSignUpTO registration = new UserSignUpTO();
        registration.setEmail("test@example.com");
        registration.setUserName("username");

        when(userRepository.existsByUserName(registration.getUserName())).thenReturn(true);

        // Act
        ArrayList<String> errors = userService.validateUserSignUpTO(registration, bindingResult);

        // Assert
        assertFalse(errors.isEmpty());
        assertEquals("Username is already in use.", errors.get(0));
    }

    @Test
    public void testValidateUserSignUpTO_InvalidEmail() {
        // Arrange
        UserSignUpTO registration = new UserSignUpTO();
        registration.setEmail("invalid-email");
        registration.setUserName("username");

        // Act
        ArrayList<String> errors = userService.validateUserSignUpTO(registration, bindingResult);

        // Assert
        assertFalse(errors.isEmpty());
        assertEquals("Email is not valid.", errors.get(0));
    }

    @Test
    public void testValidateUserSignUpTO_BindingErrors() {
        // Arrange
        UserSignUpTO registration = new UserSignUpTO();
        registration.setEmail("test@example.com");
        registration.setUserName("username");

        ObjectError error = new ObjectError("testError", "test message");
        when(bindingResult.hasErrors()).thenReturn(true);
        when(bindingResult.getAllErrors()).thenReturn(Collections.singletonList(error));

        // Act
        ArrayList<String> errors = userService.validateUserSignUpTO(registration, bindingResult);

        // Assert
        assertFalse(errors.isEmpty());
        assertTrue(errors.contains(error.toString()));
    }

    @Test
    public void testGetAuthenticatedUserId() {
        // Arrange
        UserDetailsImpl mockUserDetails = mock(UserDetailsImpl.class);
        when(authentication.getPrincipal()).thenReturn(mockUserDetails);
        when(mockUserDetails.getId()).thenReturn(1L);

        // Act
        Long authenticatedUserId = userService.getAuthenticatedUserId(authentication);

        // Assert
        assertEquals(1L, authenticatedUserId);
    }

    @Test
    public void testGetAuthenticatedUser_UserFound() {
        // Arrange
        User user = new User();
        user.setId(1L);

        UserDetailsImpl mockUserDetails = mock(UserDetailsImpl.class);
        when(authentication.getPrincipal()).thenReturn(mockUserDetails);
        when(mockUserDetails.getId()).thenReturn(1L);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        // Act
        User authenticatedUser = userService.getAuthenticatedUser(authentication);

        // Assert
        assertEquals(user, authenticatedUser);
    }

    @Test
    public void testGetAuthenticatedUser_UserNotFound() {
        // Arrange
        UserDetailsImpl mockUserDetails = mock(UserDetailsImpl.class);
        when(authentication.getPrincipal()).thenReturn(mockUserDetails);
        when(mockUserDetails.getId()).thenReturn(1L);

        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> userService.getAuthenticatedUser(authentication));
    }

    @Test
    public void deleteUserAndSubjects_UserExistsAndDeletedSuccessfully() {
        // Arrange
        Long userId = 1L;
        User user = new User();
        user.setId(userId);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        doNothing().when(userSubjectRepository).deleteAllByUserId(userId);
        doNothing().when(userRepository).delete(user);

        // Act
        boolean result = userService.deleteUserAndSubjects(userId);

        // Assert
        assertTrue(result);
        verify(userSubjectRepository, times(1)).deleteAllByUserId(userId);
        verify(userRepository, times(1)).delete(user);
    }

    @Test
    public void deleteUserAndSubjects_UserDoesNotExist() {
        // Arrange
        Long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Act
        boolean result = userService.deleteUserAndSubjects(userId);

        // Assert
        assertFalse(result);
        verify(userSubjectRepository, never()).deleteAllByUserId(anyLong());
        verify(userRepository, never()).delete(any(User.class));
    }

    @Test
    public void deleteSubjectBeforeUser() {
        // Arrange
        Long userId = 1L;
        User user = new User();
        user.setId(userId);
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        InOrder inOrder = inOrder(userSubjectRepository, userRepository);

        // Act
        boolean result = userService.deleteUserAndSubjects(userId);

        // Assert
        assertTrue(result);
        inOrder.verify(userSubjectRepository).deleteAllByUserId(userId);
        inOrder.verify(userRepository).delete(user);

    }
}