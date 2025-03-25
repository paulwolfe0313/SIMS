package com.sims.auth.service;

import com.sims.auth.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender emailSender;

    @Value("${app.baseUrl}")
    private String baseUrl;

    @Value("${spring.mail.username}")
    private String fromEmail;

    public void sendVerificationEmail(User user) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(user.getEmail());
        message.setSubject("Please verify your email");
        message.setText(String.format(
            "Dear %s,\n\n" +
            "Please click on the below link to verify your email:\n" +
            "%s/auth/verify-email?token=%s\n\n" +
            "This link will expire in 24 hours.\n\n" +
            "Best regards,\n" +
            "SIMS Team",
            user.getFirstName(),
            baseUrl,
            user.getVerificationToken()
        ));

        emailSender.send(message);
    }

    public void sendPasswordResetEmail(User user, String token) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(user.getEmail());
        message.setSubject("Password Reset Request");
        message.setText(String.format(
            "Dear %s,\n\n" +
            "You have requested to reset your password. Please click on the below link to reset your password:\n" +
            "%s/auth/reset-password?token=%s\n\n" +
            "This link will expire in 24 hours.\n" +
            "If you did not request a password reset, please ignore this email.\n\n" +
            "Best regards,\n" +
            "SIMS Team",
            user.getFirstName(),
            baseUrl,
            token
        ));

        emailSender.send(message);
    }

    public void sendPasswordChangedEmail(User user) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(user.getEmail());
        message.setSubject("Password Changed Successfully");
        message.setText(String.format(
            "Dear %s,\n\n" +
            "Your password has been changed successfully.\n" +
            "If you did not make this change, please contact support immediately.\n\n" +
            "Best regards,\n" +
            "SIMS Team",
            user.getFirstName()
        ));

        emailSender.send(message);
    }
}
