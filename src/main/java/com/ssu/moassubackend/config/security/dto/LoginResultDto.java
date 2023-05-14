package com.ssu.moassubackend.config.security.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class LoginResultDto {

    private boolean success;
    private String token;
    private String msg;
    private String userName;

    private String email;

    @Builder
    public LoginResultDto(boolean success, String token, String msg, String userName, String email) {
        this.success = success;
        this.token = token;
        this.msg = msg;
        this.userName = userName;
        this.email = email;
    }

}
