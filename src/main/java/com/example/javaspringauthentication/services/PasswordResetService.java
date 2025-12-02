package com.example.javaspringauthentication.services;

import com.example.javaspringauthentication.entities.PasswordResetToken;
import com.example.javaspringauthentication.entities.User;
import com.example.javaspringauthentication.repositories.PasswordResetTokenRepository;
import com.example.javaspringauthentication.repositories.UserRepository;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class PasswordResetService {

    private final UserRepository userRepository;

    private final PasswordResetTokenRepository passwordResetTokenRepository;

    private final PasswordEncoder passwordEncoder;

    private final EmailService emailService;

    @Autowired
    public PasswordResetService(UserRepository userRepository,
                                PasswordResetTokenRepository passwordResetTokenRepository,
                                PasswordEncoder passwordEncoder,
                                EmailService emailService) {
        this.userRepository = userRepository;
        this.passwordResetTokenRepository = passwordResetTokenRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
    }

    public void initPasswordReset(String email) throws MessagingException {
        User user = userRepository.findByEmail(email);

        if (user == null) {
            return;
        }

        String token = UUID.randomUUID().toString();

        PasswordResetToken passwordResetToken = new PasswordResetToken();
        passwordResetToken.setToken(token);
        passwordResetToken.setUser(user);
        passwordResetToken.setExpiryDate(LocalDateTime.now().plusMinutes(30));
        passwordResetTokenRepository.save(passwordResetToken);

        // TODO : send an email message
        emailService.sendEmail(user.getEmail(), "Password reset link", email);
    }

    public void resetPassword(String token, String newPassword) {

        PasswordResetToken passwordResetToken = passwordResetTokenRepository.findByToken(token)
                                                    .orElseThrow(() -> new RuntimeException("token not found"));

        if (passwordResetToken.isExpired()) {
            throw new RuntimeException("token has expired");
        }

        User user = passwordResetToken.getUser();

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);

        passwordResetTokenRepository.delete(passwordResetToken);

    }

}
