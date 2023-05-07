package com.ssu.moassubackend.config.oauth.controller;


import com.ssu.moassubackend.config.oauth.service.OAuth2TokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class LoginController {

    private final OAuth2TokenService oauth2TokenService;

    @GetMapping("/oauth/token")
    public String getToken(@RequestParam(name = "code") String authorizationCode) {
        log.info("[getToken] authorization code = {}", authorizationCode);
        String kakaoAccessToken = oauth2TokenService.getKakaoAccessToken(authorizationCode);
        log.info("[access Token] {}", kakaoAccessToken);
        return kakaoAccessToken;
    }
}
