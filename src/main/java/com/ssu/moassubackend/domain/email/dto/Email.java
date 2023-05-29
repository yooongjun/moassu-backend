package com.ssu.moassubackend.domain.email.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
public class Email {

    private String to;
    private String subject;
    private String content;

    @Builder
    public Email(String to, String subject, String content) {
        this.to = to;
        this.subject = subject;
        this.content = content;
    }
}
