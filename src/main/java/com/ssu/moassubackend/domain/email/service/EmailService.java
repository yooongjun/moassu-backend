package com.ssu.moassubackend.domain.email.service;

import com.ssu.moassubackend.domain.email.dto.Email;
import com.ssu.moassubackend.domain.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    private final UserRepository userRepository;

    @Autowired
    public EmailService(JavaMailSender javaMailSender, UserRepository userRepository) {
        this.mailSender = javaMailSender;
        this.userRepository = userRepository;
    }

    public void sendMail(Email email) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(email.getTo());
        simpleMailMessage.setSubject(email.getSubject());
        simpleMailMessage.setText(email.getContent());


        mailSender.send(simpleMailMessage);
    }

}
