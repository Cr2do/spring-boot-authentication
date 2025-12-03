package com.example.javaspringauthentication.global;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class EmailServiceImpl implements IEmailService {

    private final JavaMailSender mailSender;

    private final TemplateEngine templateEngine;

    @Autowired
    public EmailServiceImpl(JavaMailSender mailSender, TemplateEngine templateEngine) {
        this.mailSender = mailSender;
        this.templateEngine = templateEngine;
    }

    @Value("${app.mail}")
    private String fromEmail;

    @Async
    public void sendEmail(String to, String subject, String text) {
        try {
            Context context = new Context();

            context.setVariable("email", to);
            context.setVariable("resetLink", subject + text);

            String process = templateEngine.process("password-reset", context);

            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

            helper.setFrom(fromEmail);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(process, true);

            mailSender.send(mimeMessage);
            System.out.println("Email Sent" + to);
        } catch (MessagingException e) {
            e.printStackTrace();
            System.out.println("Email Failed" + to);
        }
    }

}
