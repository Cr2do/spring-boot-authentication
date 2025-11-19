package com.example.javaspringauthentication.services.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Date;

@Component
public class AuthenticationUtils {


    @Value("${spring.jwt.secret}")
    private String jwtSecretValue;

    @Value("${spring.jwt.expiration}")
    private Long jwtExpirationTime;

    public boolean isValidToken(String token) {
        try {
            Claims claims = extractAllClaims(token);
            return claims.getExpiration().after(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    public UserDetails getUserDetailsFromToken(String token) {
        Claims claims = extractAllClaims(token);
        String username = claims.getSubject();

        // I've personalised here to fetch roles and some another information
        return User.withUsername(username).password("").authorities("USER").build();
    }

    public String generateToken(String username) {
        SecretKey key = Keys.hmacShaKeyFor(jwtSecretValue.getBytes());

        // on the documentation in the readme not mentioned, but I've added inside it
        return Jwts.builder()
                .signWith(key)
                .subject(username)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + jwtExpirationTime))
                .compact();

    }

    private Claims extractAllClaims(String token) {
        SecretKey key = Keys.hmacShaKeyFor(jwtSecretValue.getBytes());
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

}
