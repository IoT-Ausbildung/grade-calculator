package com.example.gradecalculator.registration;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignupValidation {

    public static void main(String[] agrs) {

        String email = "louis.huefner@maibornwolff.de";
        System.out.println(valEmail(email));
        String password = "Louis1325!";
        System.out.println(valPassword(password));

    }

    public static boolean valEmail(String email) {
        String emailRegex = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$";
        Pattern emailPat = Pattern.compile(emailRegex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = emailPat.matcher(email);
        return matcher.find();
    }

    public static boolean valPassword(String password) {
        if (password.length() < 8) {
            return false;
        }
        if (!password.matches(".*[A-Z].*")) {
            return false;
        }
        if (!password.matches(".*[a-z].*")) {
            return false;
        }
        if (!password.matches(".*[!@#$%^&*(),.?\":{}|<>].*")) {
            return false;
        }
        return password.matches(".*[0-9].*");
    }
}