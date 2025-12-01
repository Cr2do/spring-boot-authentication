package com.example.javaspringauthentication.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PasswordResetToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String token;

    private LocalDateTime expiryDate;

    @OneToOne(fetch =  FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User  user;

    public boolean isExpired() {
        return LocalDateTime.now().isAfter(this.expiryDate);
    }

}
