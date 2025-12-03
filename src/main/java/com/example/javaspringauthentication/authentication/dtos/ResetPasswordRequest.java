package com.example.javaspringauthentication.authentication.dtos;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResetPasswordRequest {

    private String token;
    private String newPassword;

}
