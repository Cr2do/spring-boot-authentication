package com.example.javaspringauthentication.authentication.services;

import com.example.javaspringauthentication.authentication.dtos.LoginCreateDTO;
import com.example.javaspringauthentication.authentication.dtos.RegisterDTO;
import org.springframework.http.ResponseEntity;

public interface IAuthenticationService {

    ResponseEntity<?> register(RegisterDTO registerDTO);

    ResponseEntity<?> login(LoginCreateDTO loginCreateDTO);

    void initPasswordReset(String email);

    void resetPassword(String token, String newPassword);

}
