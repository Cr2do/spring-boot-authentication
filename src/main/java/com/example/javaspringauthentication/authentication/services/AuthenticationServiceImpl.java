package com.example.javaspringauthentication.authentication.services;

import com.example.javaspringauthentication.authentication.PasswordResetToken;
import com.example.javaspringauthentication.authentication.PasswordResetTokenRepository;
import com.example.javaspringauthentication.global.IEmailService;
import com.example.javaspringauthentication.user.User;
import com.example.javaspringauthentication.user.UserRepository;
import com.example.javaspringauthentication.config.AuthenticationUtils;
import com.example.javaspringauthentication.authentication.dtos.LoginCreateDTO;
import com.example.javaspringauthentication.authentication.dtos.LoginResponseDTO;
import com.example.javaspringauthentication.authentication.dtos.RegisterDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class AuthenticationServiceImpl implements IAuthenticationService {

    private final UserRepository userRepository;

    private final AuthenticationManager authenticationManager;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationUtils authenticationUtils;

    private final PasswordResetTokenRepository passwordResetTokenRepository;

    private final IEmailService iEmailService;

    public AuthenticationServiceImpl(
            AuthenticationManager authenticationManager,
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            AuthenticationUtils authenticationUtils,
            PasswordResetTokenRepository passwordResetTokenRepository,
            IEmailService iEmailService
    ) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationUtils = authenticationUtils;
        this.passwordResetTokenRepository = passwordResetTokenRepository;
        this.iEmailService = iEmailService;
    }


    public ResponseEntity<?> register(RegisterDTO registerDTO) {

        User user = new User()
                .setEmail(registerDTO.getEmail())
                .setFirstname(registerDTO.getFirstname())
                .setLastname(registerDTO.getLastname())
                .setPassword(passwordEncoder.encode(registerDTO.getPassword()));

        return  ResponseEntity.ok(this.userRepository.save(user));

    }


    public ResponseEntity<?> login(LoginCreateDTO loginCreateDTO) {

        User user = userRepository.findByEmail(loginCreateDTO.getEmail());

        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not found");
        }

        this.authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                loginCreateDTO.getEmail(),
                loginCreateDTO.getPassword()
            )
        );

        String token = this.authenticationUtils.generateToken(loginCreateDTO.getEmail());

        LoginResponseDTO loginResponseDTO = new LoginResponseDTO(
            loginCreateDTO.getEmail(),
            token
        );

        return ResponseEntity.ok(loginResponseDTO);

    }


    public void initPasswordReset(String email) {

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
        iEmailService.sendEmail(user.getEmail(), "Password reset link", email);

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
