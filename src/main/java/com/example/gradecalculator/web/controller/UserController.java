package com.example.gradecalculator.web.controller;

import com.example.gradecalculator.entities.UserType;
import com.example.gradecalculator.enums.UserNames;
import com.example.gradecalculator.mapper.UserMapper;
import com.example.gradecalculator.service.UserService;
import com.example.gradecalculator.repository.UserRepository;
import com.example.gradecalculator.repository.UserTypeRepository;
import com.example.gradecalculator.web.model.UserSignUpTO;
import jakarta.validation.Valid;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
public class UserController {

    private final UserRepository userRepository;
    private final UserTypeRepository userTypeRepository;
    private UserMapper userMapper = Mappers.getMapper(UserMapper.class);

    @Autowired
    private UserService userService;
    @Autowired
    public UserController(UserRepository userRepository, UserTypeRepository userTypeRepository) {
        this.userRepository = userRepository;
        this.userTypeRepository = userTypeRepository;
    }

    @GetMapping("/register")
    public String registerGet(Model model){
        var userTypes = userTypeRepository.findAll();
        model.addAttribute("userTypes", userTypes);
        var form = new UserSignUpTO();
        model.addAttribute("registration", form);
        return "register";
    }

    @GetMapping("/myProfile/{id}")
    public String myProfileGet(@PathVariable("id") int porfileId, Model model){
        var myProfileTest = userRepository.findById(porfileId);
        var myProfile = userMapper.dataToTO(myProfileTest);
        model.addAttribute("myProfile", myProfile);
        return "myProfile";
    }

    @GetMapping("/user")
    public String userGet(Model model){
        var userTest = userRepository.findAll();
        var users = userMapper.dataToTO(userTest);
        model.addAttribute("users", users);
        return "user";
    }

    @PostMapping("/register")
    private String registerPost(Model model, @Valid @ModelAttribute UserSignUpTO registration, BindingResult bindingResult) {

        var errors = validateUserSignUpTO(registration, bindingResult);

        if(errors.isEmpty()){
            var user = userService.createUser(registration);
            return "HomePage";
        }

        var userTypes = userTypeRepository.findAll();
        model.addAttribute("userTypes", userTypes);
        model.addAttribute("registration", registration);
        model.addAttribute("itemErrors", errors);
        return "register";
    }

    private ArrayList<String> validateUserSignUpTO(UserSignUpTO registration, BindingResult bindingResult) {
        var errors = new ArrayList<String>();

        if(userRepository.existsByEmail(registration.getEmail())) {
            errors.add("Email is already in use.");
        }

        if(userRepository.existsByUserName(registration.getUserName())){
            errors.add("Username is already in use.");
        }

        if(registration.getEmail() == null || !validate(registration.getEmail())){
            errors.add("Email is not valid.");
        }

        if(bindingResult.hasErrors()){
           errors.addAll(bindingResult.getAllErrors().stream().map(ObjectError::toString).toList());
        }
        return errors;
    }

    public static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    public static boolean validate(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.matches();
    }
}