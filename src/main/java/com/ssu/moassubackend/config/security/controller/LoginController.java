package com.ssu.moassubackend.config.security.controller;


import com.ssu.moassubackend.config.security.SocialType;
import com.ssu.moassubackend.config.security.dto.LoginResultDto;
import com.ssu.moassubackend.config.security.dto.OAuthAttributes;
import com.ssu.moassubackend.config.security.service.LoginService;
import com.ssu.moassubackend.config.security.service.OAuth2TokenService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/oauth2/login")
public class LoginController {

    private final OAuth2TokenService oauth2TokenService;
    private final LoginService loginService;

    @PostMapping("/kakao/{code}")
    public ResponseEntity<Map<String, String>> login(@PathVariable(name = "code") String authorizationCode) {

        log.info("[Login Controller] authorization code = {}", authorizationCode);
        if (authorizationCode.isEmpty()) {
            throw new RuntimeException("잘못된 요청입니다. (Authorization Code is empty.) ");
        }

        OAuthAttributes userInfo = oauth2TokenService.getUserInfo(authorizationCode, SocialType.KAKAO);
        LoginResultDto loginResultDto = loginService.login(userInfo);

        if (loginResultDto.isSuccess()) {

            MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
            headers.add("M-AUTH-TOKEN", loginResultDto.getToken());

            ConcurrentHashMap<String, String> body = new ConcurrentHashMap<>();
            body.put("name", loginResultDto.getUserName());
            body.put("msg", loginResultDto.getMsg());
            body.put("email", loginResultDto.getEmail());

            return ResponseEntity.status(HttpStatus.ACCEPTED).headers(new HttpHeaders(headers)).body(body);
        }

        throw new RuntimeException();
    }

    @ExceptionHandler(RuntimeException.class)
    public void exceptionHandler(HttpServletResponse response) throws IOException {
        response.sendRedirect("/");
    }
}
