package com.example.gradecalculator.service;

import com.example.gradecalculator.entities.User;
import com.example.gradecalculator.entities.UserType;
import com.example.gradecalculator.mapper.UserRegistrationMapper;
import com.example.gradecalculator.model.UserDetailsImpl;
import com.example.gradecalculator.model.UserEditTO;
import com.example.gradecalculator.repository.UserRepository;
import com.example.gradecalculator.repository.UserSubjectRepository;
import com.example.gradecalculator.repository.UserTypeRepository;
import com.example.gradecalculator.model.UserSignUpTO;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserTypeRepository userTypeRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRegistrationMapper userRegistrationMapper;
    private final UserSubjectRepository userSubjectRepository;

    @Autowired
    public UserService(UserRepository userRepository,
                       UserTypeRepository userTypeRepository,
                       PasswordEncoder passwordEncoder,
                       UserRegistrationMapper userRegistrationMapper, UserSubjectRepository userSubjectRepository) {
        this.userRepository = userRepository;
        this.userTypeRepository = userTypeRepository;
        this.passwordEncoder = passwordEncoder;
        this.userRegistrationMapper = userRegistrationMapper;
        this.userSubjectRepository = userSubjectRepository;
    }

    public User createUser(UserSignUpTO registration) {
        String encodedPassword = passwordEncoder.encode(registration.getPassword());

        User user = userRegistrationMapper.TOToEntity(registration);
        user.setEncodedPassword(encodedPassword);

        UserType userType = userTypeRepository.findById(registration.getUserType()).orElseThrow(()
                -> new IllegalArgumentException("Invalid user type ID: " + registration.getUserType()));
        user.setUserType(userType);
        userRepository.save(user);
        return user;
    }

    public User editProfile(Long userId, UserEditTO editProfile) {
        var userResult = userRepository.findById(userId);
        if (userResult.isEmpty()) {
            throw new IllegalArgumentException("User not found");
        }

        var user = userResult.get();
        user.setFirstName(editProfile.getFirstName());
        user.setLastName(editProfile.getLastName());
        userRepository.save(user);

        return user;
    }

    public List<String> editPasswordService(String oldPassword, String newPassword, User userData) {
        var encodedPassword = userData.getEncodedPassword();
        var errors = new ArrayList<String>();

        if(passwordEncoder.matches(oldPassword, encodedPassword)) {
            if(!newPassword.equals(oldPassword)) {
                userData.setEncodedPassword(passwordEncoder.encode(newPassword));
                userRepository.save(userData);
            }
            else {
                errors.add("You are currently using this password.");
            }
        }
        else {
            errors.add("Passwords do not match.");
        }

        return errors;
    }

    public ArrayList<String> validateUserSignUpTO(UserSignUpTO registration, BindingResult bindingResult) {
        var errors = new ArrayList<String>();

        if (userRepository.existsByEmail(registration.getEmail())) {
            errors.add("Email is already in use.");
        }

        if (userRepository.existsByUserName(registration.getUserName())) {
            errors.add("Username is already in use.");
        }

        if (registration.getEmail() == null || !validate(registration.getEmail())) {
            errors.add("Email is not valid.");
        }

        if (bindingResult.hasErrors()) {
            errors.addAll(bindingResult.getAllErrors().stream().map(ObjectError::toString).toList());
        }

        return errors;
    }

    public static boolean validate(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.matches();
    }

    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
            Pattern.CASE_INSENSITIVE);

    public Long getAuthenticatedUserId(Authentication authentication) {
        var userService = (UserDetailsImpl)authentication.getPrincipal();
        return userService.getId();
    }

    public User getAuthenticatedUser(Authentication authentication) {
        var userAuthenticated = getAuthenticatedUserId(authentication);
        var userOptional = userRepository.findById(userAuthenticated);
        if (userOptional.isPresent()) {
            return userOptional.get();
        } else {
            throw new IllegalArgumentException("User not found");
        }
    }

    @Transactional
    public boolean deleteUserAndSubjects(Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            System.out.println("User not found with ID: " + userId);
            return false;
        }
        userSubjectRepository.deleteAllByUserId(userId);
        userRepository.delete(userOptional.get());
        System.out.println("User with ID " + userId + " and all associated data successfully deleted.");
        return true;
    }
}