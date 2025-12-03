package com.example.javaspringauthentication.authentication.controllers;

import com.example.javaspringauthentication.authentication.dtos.ForgotPasswordRequest;
import com.example.javaspringauthentication.authentication.dtos.ResetPasswordRequest;
import com.example.javaspringauthentication.authentication.services.AuthenticationServiceImpl;
import com.example.javaspringauthentication.authentication.dtos.LoginCreateDTO;
import com.example.javaspringauthentication.authentication.dtos.RegisterDTO;
import com.example.javaspringauthentication.authentication.services.IAuthenticationService;
import jakarta.mail.MessagingException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping
@RestController
public class AuthenticationController {


    private final IAuthenticationService iAuthenticationService;

    public AuthenticationController(IAuthenticationService iAuthenticationService) {
        this.iAuthenticationService = iAuthenticationService;
    }


    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterDTO registerDTO) {
        return ResponseEntity.ok(this.iAuthenticationService.register(registerDTO));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginCreateDTO loginCreateDTO) {
        return ResponseEntity.ok(this.iAuthenticationService.login(loginCreateDTO));
    }


    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestBody ForgotPasswordRequest forgotPasswordRequest) throws MessagingException {
        this.iAuthenticationService.initPasswordReset(forgotPasswordRequest.getEmail());
        return ResponseEntity.ok("If email exist, reset link sent");
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody ResetPasswordRequest resetPasswordRequest) {
        this.iAuthenticationService.resetPassword(resetPasswordRequest.getToken(), resetPasswordRequest.getNewPassword());
        return ResponseEntity.ok("Password modified");
    }


}
