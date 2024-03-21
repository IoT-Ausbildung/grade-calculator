package com.example.gradecalculator.web.controller;
import com.example.gradecalculator.entities.User;
import com.example.gradecalculator.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@SpringBootApplication
public class FormController {

    private final UserRepository userRepository;

    @Autowired
    public FormController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/submit")
    @ResponseBody
    public String handleSubmit(@RequestParam String name, @RequestParam String email, @RequestParam String message) {
        User user = new User(name, email, message);
        userRepository.save(user);
        System.out.println("Name: " + name);
        System.out.println("E-Mail: " + email);
        System.out.println("Nachricht: " + message);
        return "Daten wurden im Terminal angezeigt.";
    }

    public static void main(String[] args) {
        SpringApplication.run(FormController.class, args);
    }
}
