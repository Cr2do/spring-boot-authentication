package com.example.javaspringauthentication.services.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginCreateDTO {

    private String email;
    private String password;

}
