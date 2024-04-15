package com.example.gradecalculator.Verification;

import java.util.Random;

public class VerificationCodeGenerator {

    public String generateCode() {
        Random random = new Random();
        int code = random.nextInt(900000) + 100000;
        return String.valueOf(code);
    }
}