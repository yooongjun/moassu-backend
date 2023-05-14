package com.ssu.moassubackend.config.security.dto;

import lombok.Getter;

@Getter
public class OauthToken {

    private String access_token;
    private String refresh_token;

}
