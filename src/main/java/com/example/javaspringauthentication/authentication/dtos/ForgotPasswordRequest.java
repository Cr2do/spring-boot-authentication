package com.example.javaspringauthentication.authentication.dtos;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ForgotPasswordRequest {

    private String email;

}
