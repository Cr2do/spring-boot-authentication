package com.example.javaspringauthentication.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Arrays;
import java.util.Map;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final AuthenticationUtils authenticationUtils;

    @Autowired
    public SecurityConfig(AuthenticationUtils authenticationUtils) {
        this.authenticationUtils = authenticationUtils;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
            .csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(
                req ->
                    req
                        .requestMatchers(
                            "/h2-console/**",
                            "/register",
                            "/login",
                            "/forgot-password",
                            "/reset-password",
                            // TODO : Never broke these links below there are useful for Swagger
                            "/swagger-ui/**",
                            "/swagger-ui.html",
                            "/v3/api-docs/**",
                            "/v3/api-docs.yaml",
                            "/context-path/**"
                        )
                        .permitAll()
                        .anyRequest()
                        .authenticated()
            )
            .addFilterBefore(
                new AuthenticationTokenFilter(this.authenticationUtils),
                UsernamePasswordAuthenticationFilter.class
            );

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            CustomUserDetailsService customUserDetailsService,
            PasswordEncoder passwordEncoder
    ) throws Exception {

        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider(
                customUserDetailsService
        );
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);

        ProviderManager providerManager = new ProviderManager(Arrays.asList(daoAuthenticationProvider));
        providerManager.setEraseCredentialsAfterAuthentication(true);

        return providerManager;
    }



    @Bean
    public PasswordEncoder passwordEncoder() {
        String idForEncode = "bcrypt";
        return new DelegatingPasswordEncoder(
            idForEncode,
            Map.of(idForEncode, new BCryptPasswordEncoder())
        );
    }

}
