package com.example.gradecalculator.web.controller;

import com.example.gradecalculator.entities.UserType;
import com.example.gradecalculator.repository.UserTypeRepository;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class FormController {

    private final UserTypeRepository userTypeRepository;

    @Autowired
    public FormController(UserTypeRepository userTypeRepository) {
        this.userTypeRepository = userTypeRepository;
    }

    @PostMapping("/submit")
    @ResponseBody
    public String handleSubmit(@RequestParam String name, @RequestParam String email, @RequestParam String message, @RequestParam Long userTypeId) {
        UserType userType = userTypeRepository.findById(userTypeId).orElse(null);

        // ToDo insert user
        // ConcreteUser user = new ConcreteUser(name, email, message, userType);
        // userTypeRepository.save(user);

        return "Data has been saved in the backend.";
    }
}
