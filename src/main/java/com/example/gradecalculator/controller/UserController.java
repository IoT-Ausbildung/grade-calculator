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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class UserController {
    private final UserRepository userRepository;
    private final UserTypeRepository userTypeRepository;
    private UserMapper userMapper = Mappers.getMapper(UserMapper.class);

    @Autowired
    private PasswordEncoder passwordEncoder;

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
        var userAuthenticated = userService.getAuthenticatedUserId(authentication);
        var userID = userRepository.findById(userAuthenticated);
        var userData = userMapper.dataToTO(userID.get());
        model.addAttribute("editProfile", userData);

        return "editProfile";
    }

    @GetMapping("/editPassword")
    public String editPasswordGet(Model model, Authentication authentication){
        var userAuthenticated = userService.getAuthenticatedUserId(authentication);
        var userID = userRepository.findById(userAuthenticated);
        var userData = userMapper.dataToTO(userID.get());
        return "editPassword";
    }

    @GetMapping("/user")
    public String userGet(Model model) {
        var userTest = userRepository.findAll();
        var users = userMapper.dataToTO(userTest);
        model.addAttribute("users", users);

        return "user";
    }

    @PostMapping("/editPassword")
    public String editPasswordPost(@RequestParam("oldPassword") String oldPassword, @RequestParam("newPassword") String newPassword, Authentication authentication, Model model){

        var userAuthenticated = userService.getAuthenticatedUserId(authentication);
        var userId = userRepository.findById(userAuthenticated);
        var userData = userId.get();
        var encodedPassword = userData.getEncodedPassword();

        String encodedPasswordHash = encodedPassword;
        var errors = userService.validateEditPassword(oldPassword, newPassword, encodedPasswordHash);

        if(passwordEncoder.matches(oldPassword, encodedPassword)){
            userData.setEncodedPassword(passwordEncoder.encode(newPassword));
            userRepository.save(userData);
            return "login";
        }

        model.addAttribute("errors", errors); //in html die error messages anzeigen (schau dir das vom singup an wie des da gemacht wurde)
        return "editPassword";
    }

    @PostMapping("/editProfile")
    private String editProfilePost(@Valid @ModelAttribute UserEditTO editProfile, Authentication authentication){
        var userID = userService.getAuthenticatedUserId(authentication);
        var user = userService.editProfile(userID, editProfile);
        return "index";
    }

    @PostMapping("/signup")
    private String signupPost(Model model, @Valid @ModelAttribute UserSignUpTO registration, BindingResult bindingResult) {
        var errors = userService.validateUserSignUpTO(registration, bindingResult);

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

    @PostMapping("/logout")
    public String performLogout(Authentication authentication, HttpServletRequest request, HttpServletResponse response) {
        this.logoutHandler.logout(request, response, authentication);
        return "index";
    }

    SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();
}