package com.example.javaspringauthentication.services;

import com.example.javaspringauthentication.entities.User;
import com.example.javaspringauthentication.repositories.UserRepository;
import com.example.javaspringauthentication.services.dto.RegisterDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private UserRepository userRepository;

    private AuthenticationManager authenticationManager;

    private PasswordEncoder passwordEncoder;

    public AuthService(
            AuthenticationManager authenticationManager,
            UserRepository userRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    public ResponseEntity<?> register(RegisterDTO registerDTO) {

        User user = new User()
                .setEmail(registerDTO.getEmail())
                .setFirstname(registerDTO.getFirstname())
                .setLastname(registerDTO.getLastname())
                .setPassword(passwordEncoder.encode(registerDTO.getPassword()));

        return  ResponseEntity.ok(this.userRepository.save(user));

    }


}
