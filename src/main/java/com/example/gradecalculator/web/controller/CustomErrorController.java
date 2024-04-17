package com.example.gradecalculator.web.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if (status != null) {
            Integer statusCode = (Integer) status;

            switch (statusCode) {
                case HttpStatus.NOT_FOUND.value():
                    model.addAttribute("message", "Error " + statusCode + ": Page not found");
                    break;
                case HttpStatus.INTERNAL_SERVER_ERROR_VALUE:
                    model.addAttribute("message", "Error " + statusCode + ": Internal server error");
                    break;
                default:
                    model.addAttribute("message", "Error " + statusCode + ": An error occurred");
                    break;
            }
        } else {
            model.addAttribute("message", "An unexpected error occurred");
        }
        return "error";
    }
}