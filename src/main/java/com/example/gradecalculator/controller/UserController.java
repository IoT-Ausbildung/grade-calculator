package com.example.gradecalculator.controller;

import com.example.gradecalculator.mapper.UserMapper;
import com.example.gradecalculator.model.UserDetailsImpl;
import com.example.gradecalculator.model.UserEditTO;
import com.example.gradecalculator.repository.UserRepository;
import com.example.gradecalculator.repository.UserTypeRepository;
import com.example.gradecalculator.service.UserService;
import com.example.gradecalculator.model.UserSignUpTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
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
    private UserMapper userMapper = Mappers.getMapper(UserMapper.class);

    @Autowired
    private UserService userService;


    @Autowired
    public UserController(UserRepository userRepository, UserTypeRepository userTypeRepository) {
        this.userRepository = userRepository;
        this.userTypeRepository = userTypeRepository;
    }


    @GetMapping("/signup")
    public String signupGet(Model model) {
        var userTypes = userTypeRepository.findAll();
        model.addAttribute("userTypes", userTypes);

        var form = new UserSignUpTO();
        model.addAttribute("registration", form);

        return "signup";
    }

    @GetMapping("/myProfile")
    public String myProfileGet(Model model, Authentication authentication){
        var userTypes = userTypeRepository.findAll();
        model.addAttribute("userTypes", userTypes);

        var userService = (UserDetailsImpl)authentication.getPrincipal();
        var user = userRepository.findById(userService.getId());
        var myProfile = userMapper.dataToTO(user.get());
        model.addAttribute("myProfile", myProfile);

        return "myProfile";
    }

    @GetMapping("/editProfile")
    public String editProfileGet(Model model, Authentication authentication){
        var userTypes = userTypeRepository.findAll();
        model.addAttribute("userTypes", userTypes);

        var userService = (UserDetailsImpl)authentication.getPrincipal();
        var user = userRepository.findById(userService.getId());
        var editProfile = userMapper.dataToTO(user.get());
        model.addAttribute("editProfile", editProfile);

        return "editProfile";
    }

    @GetMapping("/user")
    public String userGet(Model model) {
        var userTest = userRepository.findAll();
        var users = userMapper.dataToTO(userTest);
        model.addAttribute("users", users);

        return "user";
    }

    @PostMapping("/editProfile")
    private String editProfilePost(@Valid @ModelAttribute UserEditTO editProfile, Authentication authentication){

        var userID = getAuthenticatedUserId(authentication);
        var user = userService.editProfile(userID, editProfile);
        return "index";
    }

    @PostMapping("/signup")
    private String signupPost(Model model, @Valid @ModelAttribute UserSignUpTO registration, BindingResult bindingResult) {

        var errors = validateUserSignUpTO(registration, bindingResult);

        if (errors.isEmpty()) {
            var user = userService.createUser(registration);
            return "index";
        }

        var userTypes = userTypeRepository.findAll();
        model.addAttribute("userTypes", userTypes);
        model.addAttribute("registration", registration);
        model.addAttribute("itemErrors", errors);
        return "signup";
    }

    SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();

    @PostMapping("/logout")
    public String performLogout(Authentication authentication, HttpServletRequest request, HttpServletResponse response) {
        this.logoutHandler.logout(request, response, authentication);
        return "index";
    }

    private ArrayList<String> validateUserSignUpTO(UserSignUpTO registration, BindingResult bindingResult) {
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

    public static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    public static boolean validate(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.matches();
    }

    public Long getAuthenticatedUserId(Authentication authentication) {

        var userService = (UserDetailsImpl)authentication.getPrincipal();
        return userService.getId();
    }
}