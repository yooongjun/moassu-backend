package com.ssu.moassubackend.config.oauth.controller;


import com.ssu.moassubackend.config.oauth.SocialType;
import com.ssu.moassubackend.config.oauth.dto.OAuthAttributes;
import com.ssu.moassubackend.config.oauth.service.LoginService;
import com.ssu.moassubackend.config.oauth.service.OAuth2TokenService;
import com.ssu.moassubackend.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/oauth2/login")
public class LoginController {

    private final OAuth2TokenService oauth2TokenService;
    private final LoginService loginService;

    @PostMapping("/kakao/{code}")
    public ResponseEntity<?> getUserByToken(@PathVariable(name = "code") String authorizationCode) {

        log.info("[Login Controller] authorization code = {}", authorizationCode);
        OAuthAttributes userInfo = oauth2TokenService.getUserInfo(authorizationCode, SocialType.KAKAO);

        return null;
    }
}
