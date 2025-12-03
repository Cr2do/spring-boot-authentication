package com.example.javaspringauthentication.user.services;

import org.springframework.http.ResponseEntity;

public interface IUserService {

    ResponseEntity<?> getUserInformationByEmail(String email);

}
