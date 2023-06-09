package com.ssu.moassubackend.domain.mail.service;

import com.ssu.moassubackend.domain.mail.dto.Mail;
import com.ssu.moassubackend.domain.user.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSendException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MailService {

    private final JavaMailSender mailSender;

    private final UserRepository userRepository;

    @Autowired
    public MailService(JavaMailSender javaMailSender, UserRepository userRepository) {
        this.mailSender = javaMailSender;
        this.userRepository = userRepository;
    }

    public void sendMail(String keyword, String email) {

        if (keyword == null || email == null) {
            log.info("keyword or email is null");
            return;
        }

        // set mail Information
        Mail mail = new Mail();
        mail.setSubject("[Moassu] 구독 알림");
        mail.setContent("다음 키워드의 글이 등록되었습니다.\n 키워드 : " + keyword);
        mail.setTo(email);
//        log.info("메일 발송 : {}", email);
        try {
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setTo(mail.getTo());
            simpleMailMessage.setSubject(mail.getSubject());
            simpleMailMessage.setText(mail.getContent());

            mailSender.send(simpleMailMessage);
        } catch (MailSendException exception) {
            log.error("Fail to send mail");
        }
    }

}
