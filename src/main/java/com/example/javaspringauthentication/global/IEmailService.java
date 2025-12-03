package com.example.javaspringauthentication.global;

public interface IEmailService {

    void sendEmail(String to, String subject, String text);

}
