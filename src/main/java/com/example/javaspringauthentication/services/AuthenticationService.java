package com.example.javaspringauthentication.services;

import com.example.javaspringauthentication.entities.User;
import com.example.javaspringauthentication.repositories.UserRepository;
import com.example.javaspringauthentication.config.AuthenticationUtils;
import com.example.javaspringauthentication.services.dto.LoginCreateDTO;
import com.example.javaspringauthentication.services.dto.LoginResponseDTO;
import com.example.javaspringauthentication.services.dto.RegisterDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    private final UserRepository userRepository;

    private final AuthenticationManager authenticationManager;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationUtils authenticationUtils;

    public AuthenticationService(
            AuthenticationManager authenticationManager,
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            AuthenticationUtils authenticationUtils
    ) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationUtils = authenticationUtils;
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

    public ResponseEntity<?> getUserInfoByEmail(String email) {
        return ResponseEntity.ok(this.userRepository.findByEmail(email));
    }


}
