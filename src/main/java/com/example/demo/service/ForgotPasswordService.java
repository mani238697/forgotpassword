package com.example.demo.service;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ForgotPasswordService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;

    @Transactional
    public String forgotPassword(String email) {
        // Generate a unique token for password reset
        String token = RandomStringUtils.randomAlphanumeric(20);

        // Update the user's token in the database
        User user = userRepository.findByEmail(email);
        if (user != null) {
            user.setResetToken(token);
            userRepository.save(user);

            // Send an email to the user with the password reset link including the token
            String resetLink = "http://yourwebsite.com/reset-password?token=" + token;
            String emailContent = "Please click the following link to reset your password: " + resetLink;
            emailService.sendEmail(email, "Password Reset", emailContent);
        } else {
            // Handle case where email is not found
            // You can throw an exception, return a specific response, or log the event
        }
        return token;
    }
}
