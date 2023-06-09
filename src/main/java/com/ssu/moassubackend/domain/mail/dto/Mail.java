package com.ssu.moassubackend.domain.mail.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Mail {

    private String to;
    private String subject;
    private String content;

    public Mail() {
    }

    @Builder
    public Mail(String to, String subject, String content) {
        this.to = to;
        this.subject = subject;
        this.content = content;
    }
}
