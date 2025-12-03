package com.example.javaspringauthentication.authentication.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginCreateDTO {

    private String email;
    private String password;

}
