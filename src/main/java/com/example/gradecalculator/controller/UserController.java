package com.example.gradecalculator.controller;

import com.example.gradecalculator.entities.SchoolYear;
import com.example.gradecalculator.entities.Subject;
import com.example.gradecalculator.entities.User;
import com.example.gradecalculator.entities.UserSubject;
import com.example.gradecalculator.mapper.SubjectMapper;
import com.example.gradecalculator.mapper.UserMapper;
import com.example.gradecalculator.model.UserEditTO;
import com.example.gradecalculator.model.UserSignUpTO;
import com.example.gradecalculator.repository.*;
import com.example.gradecalculator.service.SubjectService;
import com.example.gradecalculator.service.UserService;
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
import org.springframework.web.bind.annotation.*;

import java.util.*;


@Controller
public class UserController {
    private final UserRepository userRepository;
    private final UserTypeRepository userTypeRepository;
    private final SubjectRepository subjectRepository;
    private final UserSubjectRepository userSubjectRepository;
    private final SchoolYearRepository schoolYearRepository;

    private final SubjectService subjectService;
    private UserMapper userMapper = Mappers.getMapper(UserMapper.class);

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserService userService;

    @Autowired
    private SubjectMapper subjectMapper;

    @Autowired
    public UserController(UserRepository userRepository, UserTypeRepository userTypeRepository, UserSubjectRepository userSubjectRepository, SubjectRepository subjectRepository, SchoolYearRepository schoolYearRepository, SubjectService subjectService) {
        this.userRepository = userRepository;
        this.userTypeRepository = userTypeRepository;
        this.userSubjectRepository = userSubjectRepository;
        this.subjectRepository = subjectRepository;
        this.schoolYearRepository = schoolYearRepository;
        this.subjectService = subjectService;
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

        var userId = userService.getAuthenticatedUserId(authentication);
        var user = userRepository.findById(userId);
        var myProfile = userMapper.dataToTO(user.get());
        model.addAttribute("myProfile", myProfile);

        return "myProfile";
    }

    @GetMapping("/editProfile")
    public String editProfileGet(Model model, Authentication authentication){
        var userData = userService.getAuthenticatedUser(authentication);
        model.addAttribute("editProfile", userData);

        return "editProfile";
    }

    @GetMapping("/editPassword")
    public String editPasswordGet(){

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
    public String editPasswordPost(@RequestParam("oldPassword") String oldPassword, @RequestParam("newPassword")
    String newPassword, Authentication authentication, Model model){

        var userData = userService.getAuthenticatedUser(authentication);

        var errors = userService.editPasswordService(oldPassword, newPassword, userData);

        if(errors.isEmpty()){
            return "login";
        }

        model.addAttribute("errors", errors);
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
            return "login";
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


    @GetMapping("/userSubject/form")
    public String showUserSubjectForm(Model model) {
        List<SchoolYear> years = schoolYearRepository.findAll();
        List<Subject> subjects = (List<Subject>) subjectRepository.findAll();

        model.addAttribute("years", years);
        model.addAttribute("subjects", subjects);

        model.addAttribute("selectedSubjects", new ArrayList<UserSubject>());

        return "subjectSelection";
    }

    @PostMapping("/userSubject/save")
    public String saveUserSubject(@RequestParam("schoolYear") long yearId,
                                  @RequestParam("subjects") long subjectId,
                                  Authentication authentication,
                                  Model model) {
        try {
            SchoolYear selectedYear = schoolYearRepository.findById(yearId).orElseThrow(() -> new IllegalArgumentException("Year not found"));
            Subject selectedSubject = subjectRepository.findById(subjectId).orElseThrow(() -> new IllegalArgumentException("Subject not found"));
            var userID = userService.getAuthenticatedUserId(authentication);
            User selectedUser = userRepository.findById(userID).orElseThrow(() -> new IllegalArgumentException("User not found"));

            subjectService.selectSubjectForYear(selectedYear.getStartDate().getYear(), selectedSubject.getName());

            UserSubject userSubject = new UserSubject(selectedUser, selectedSubject, selectedYear);
            userSubjectRepository.save(userSubject);

            return "redirect:/userSubject/selected?year=" + selectedYear.getName() + "&user=" + userID;

        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "error";
        }
    }
    @GetMapping("userSubject/selected")
    public String showSelectedSubjects(Model model, Authentication authentication) {
        try {
            var userId = userService.getAuthenticatedUserId(authentication);

            Set<UserSubject> userSubjects = new HashSet<>(userSubjectRepository.findByUserId(userId));


            HashMap<String, Set<String>> subjectsByYear = new HashMap<>();
            for (UserSubject userSubject : userSubjects) {
                String year = userSubject.getSchoolYear().getName();
                String subject = userSubject.getSubject().getName();
                subjectsByYear.computeIfAbsent(year, k -> new HashSet<>()).add(subject);
            }

            model.addAttribute("subjectsByYear", subjectsByYear);
            model.addAttribute("user", userId);

            return "userSubjects";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "error";
        }
    }

}