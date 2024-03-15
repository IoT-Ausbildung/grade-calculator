package com.example.gradecalculator.web.controller;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@SpringBootApplication
public class FormController {

    @PostMapping("/submit")
    @ResponseBody
    public String handleSubmit(@RequestParam String name, @RequestParam String email, @RequestParam String message) {
        System.out.println("Name: " + name);
        System.out.println("E-Mail: " + email);
        System.out.println("Nachricht: " + message);
        return "Daten wurden im Terminal angezeigt.";
    }

    public static void main(String[] args) {
        SpringApplication.run(FormController.class, args);
    }

}
