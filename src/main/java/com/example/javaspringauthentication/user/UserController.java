package com.example.javaspringauthentication.user;

import com.example.javaspringauthentication.user.services.IUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private final IUserService iUserService;

    public UserController(IUserService iUserService) {
        this.iUserService = iUserService;
    }

    @GetMapping("/me")
    public ResponseEntity<?> getUser(Authentication authentication) {
        String authUserEmail = authentication.getName();
        return this.iUserService.getUserInformationByEmail(authUserEmail);
    }

}
