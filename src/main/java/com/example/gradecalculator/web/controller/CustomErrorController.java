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
        String errormessage = "An unexpected error occurred";
        String error = "";

        if (status != null) {
            Integer statusCode = Integer.valueOf(status.toString());
            switch (statusCode) {
                case 404:
                    errormessage = ": Page not found";
                    error = "Error";
                    break;
                case 500:
                    errormessage = ": Internal server error";
                    error = "Error";
                    break;
                default:
                    errormessage = ": Unknown error occurred";
                    break;
            }
        }else {
            status = "";
        }
        model.addAttribute("message", error +" "+ status + errormessage);
        return "error";
    }
}