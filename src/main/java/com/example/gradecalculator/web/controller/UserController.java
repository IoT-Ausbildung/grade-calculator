package com.example.gradecalculator.web.controller;

import com.example.gradecalculator.mapper.UserMapper;
import com.example.gradecalculator.repository.UserRepository;
import com.example.gradecalculator.repository.UserTypeRepository;
import com.example.gradecalculator.service.UserService;
import com.example.gradecalculator.web.model.LoginDTO;
import com.example.gradecalculator.web.model.UserSignUpTO;
import jakarta.validation.Valid;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
public class UserController {

    private final UserRepository userRepository;
    private final UserTypeRepository userTypeRepository;
    private final AuthenticationManager authenticationManager;
    private UserMapper userMapper = Mappers.getMapper(UserMapper.class);

    @Autowired
    private UserService userService;


    @Autowired
    public UserController(UserRepository userRepository, UserTypeRepository userTypeRepository, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.userTypeRepository = userTypeRepository;
        this.authenticationManager = authenticationManager;
    }



    @GetMapping("/register")
    public String registerGet(Model model){
        var userTypes = userTypeRepository.findAll();
        model.addAttribute("userTypes", userTypes);
        var form = new UserSignUpTO();
        model.addAttribute("registration", form);
        return "register";
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
            return "index";
        }

        var userTypes = userTypeRepository.findAll();
        model.addAttribute("userTypes", userTypes);
        model.addAttribute("registration", registration);
        model.addAttribute("itemErrors", errors);
        return "register";
    }
    @GetMapping("/login")
    public String loginUser(Model model) {
        LoginDTO loginDTO = new LoginDTO();
        model.addAttribute("loginDTO", loginDTO);
        return "login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute LoginDTO loginDTO, Model model) {
        Authentication authenticationRequest =
                new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword());
        try {
            Authentication authenticationResponse =
                    this.authenticationManager.authenticate(authenticationRequest);
            return "redirect:index";
        } catch (AuthenticationException e) {
            model.addAttribute("error", "Invalid email or password");
            return "login";
        }
    }

    private ArrayList<String> validateUserSignUpTO(UserSignUpTO registration, BindingResult bindingResult) {
        var errors = new ArrayList<String>();

        if(userRepository.existsByEmail(registration.getEmail())) {
            errors.add("Email wird schon verwendet.");
        }

        if(userRepository.existsByUserName(registration.getUserName())){
            errors.add("Benutzername wird schon verwendet.");
        }

        if(registration.getEmail() == null || !validate(registration.getEmail())){
            errors.add("Email ist nicht valide.");
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