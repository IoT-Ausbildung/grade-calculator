package com.example.gradecalculator.web.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        String errorMessage = getErrorMessage(status);
        model.addAttribute("message", errorMessage);
        return "error";
    }

    private static String getErrorMessage(Object status) {
        if (status == null) {
            return "An unexpected error occurred";
        }
        Integer statusCode = (Integer) status;
        String statusMessage = switch (statusCode) {
            case 404 -> "Page not found";
            case 500 -> "Internal server error";
            default -> "An error occurred";
        };
        return "Error " + statusCode + ": " + statusMessage;
    }
}