package com.example.gradecalculator.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class NavbarController {

    @GetMapping("/navbar")
    public String navbar() {
        return "navbar";
    }
}
