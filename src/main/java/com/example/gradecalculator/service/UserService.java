package com.example.gradecalculator.service;

import com.example.gradecalculator.entities.User;
import com.example.gradecalculator.entities.UserType;
import com.example.gradecalculator.mapper.UserRegistrationMapper;
import com.example.gradecalculator.model.UserEditTO;
import com.example.gradecalculator.repository.UserRepository;
import com.example.gradecalculator.repository.UserTypeRepository;

import com.example.gradecalculator.model.UserSignUpTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.util.ArrayList;

import static com.example.gradecalculator.controller.UserController.validate;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserTypeRepository userTypeRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRegistrationMapper userRegistrationMapper;

    @Autowired
    public UserService(UserRepository userRepository,
                       UserTypeRepository userTypeRepository,
                       PasswordEncoder passwordEncoder,
                       UserRegistrationMapper userRegistrationMapper) {

        this.userRepository = userRepository;
        this.userTypeRepository = userTypeRepository;
        this.passwordEncoder = passwordEncoder;
        this.userRegistrationMapper = userRegistrationMapper;
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
}