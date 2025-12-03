package com.example.javaspringauthentication.user.services;

import com.example.javaspringauthentication.user.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements IUserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public ResponseEntity<?> getUserInformationByEmail(String email) {
        return ResponseEntity.ok(this.userRepository.findByEmail(email));
    }

}
