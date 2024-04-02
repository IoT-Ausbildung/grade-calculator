package com.example.gradecalculator.web.controller;

import com.example.gradecalculator.mapper.UserRegistrationMapper;
import com.example.gradecalculator.registration.SignupValidation;
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
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
public class UserController {

    private final UserRepository userRepository;
    private final UserTypeRepository userTypeRepository;
    private UserRegistrationMapper userMapper = Mappers.getMapper(UserRegistrationMapper.class);

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
        form.setFirstName("Louis");
        model.addAttribute("registration", form);
        return "register";
    }

    @PostMapping("/register")
    public String registerPost(Model model, @Valid UserSignUpTO registration, BindingResult bindingResult) {

        //ein Problem der Validierung in diesem absatz

        var regexForEmail = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
        if(registration.getEmail() == null || !validate(registration.getEmail())){
            model.addAttribute("registration", registration);
            return "register";
        }

        if(bindingResult.hasErrors()){
            var errors = bindingResult.getAllErrors().stream().map(ObjectError::toString);
            model.addAttribute("itemErrors", errors);
            model.addAttribute("registration", registration);
            return "register";
        }

        var user = userMapper.TOToEntity(registration);
        userRepository.save(user);
        return "HomePage";
    }
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    public static boolean validate(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.matches();
    }
}