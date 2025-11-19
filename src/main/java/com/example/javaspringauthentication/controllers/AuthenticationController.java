package com.example.javaspringauthentication.controllers;

import com.example.javaspringauthentication.services.AuthenticationService;
import com.example.javaspringauthentication.services.dto.LoginCreateDTO;
import com.example.javaspringauthentication.services.dto.RegisterDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RequestMapping
@RestController
public class AuthenticationController {


    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }


    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterDTO registerDTO) {
        return ResponseEntity.ok(this.authenticationService.register(registerDTO));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginCreateDTO loginCreateDTO) {
        return ResponseEntity.ok(this.authenticationService.login(loginCreateDTO));
    }


    @GetMapping("/me")
    public ResponseEntity<?> getUser(Authentication authentication) {
        String authUserEmail = authentication.getName();
        return this.authenticationService.getUserInfoByEmail(authUserEmail);
    }


}
