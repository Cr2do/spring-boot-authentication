package com.example.javaspringauthentication.services.dto;

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
