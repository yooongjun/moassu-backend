package com.ssu.moassubackend.config.oauth.dto;

import lombok.Getter;

@Getter
public class OauthToken {

    private String access_token;
    private String refresh_token;

}
