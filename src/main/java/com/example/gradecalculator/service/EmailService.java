package com.example.gradecalculator.service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailService {

    public void sendVerificationEmail(String toEmail, String code) {
        try {
            Message message = new MimeMessage;
            message.setFrom(new InternetAddress("from-email@gmail.com"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject("Verification Code");
            message.setText("Your verification code is " + code);
            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}