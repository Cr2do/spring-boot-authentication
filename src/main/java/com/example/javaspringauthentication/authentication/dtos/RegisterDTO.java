package com.example.javaspringauthentication.authentication.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterDTO {

    private String email;

    private String lastname;

    private String firstname;

    private String password;

}
