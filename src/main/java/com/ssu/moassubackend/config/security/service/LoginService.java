package com.ssu.moassubackend.config.security.service;

import com.ssu.moassubackend.config.security.JwtTokenProvider;
import com.ssu.moassubackend.config.security.dto.LoginResultDto;
import com.ssu.moassubackend.config.security.dto.OAuthAttributes;
import com.ssu.moassubackend.domain.user.User;
import com.ssu.moassubackend.domain.user.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class LoginService {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public LoginService(UserRepository userRepository, JwtTokenProvider jwtTokenProvider) {
        this.userRepository = userRepository;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public LoginResultDto login(OAuthAttributes oAuthAttributes) throws RuntimeException {
        
        String oAuthId = oAuthAttributes.getOAuthId();
        Optional<User> foundUser = userRepository.findByoAuthId(oAuthId);
        String token;

        if (foundUser.isEmpty()) {
            log.info("회원가입 로직 실행");
            User user = userRepository.save(oAuthAttributes.toEntity());
            token = jwtTokenProvider.createToken(oAuthId);
            return LoginResultDto.builder().msg("회원가입을 성공했습니다.")
                    .success(true).token(token).userName(user.getNickName()).email(user.getEmail()).build();
        } else {
            log.info("로그인 로직 실행");
            User user = foundUser.get();
            user.update(oAuthAttributes.getNickName(), oAuthAttributes.getEmail());
            token = jwtTokenProvider.createToken(oAuthId);
            return LoginResultDto.builder().msg("로그인을 성공했습니다.")
                    .success(true).token(token).userName(user.getNickName()).email(user.getEmail()).build();
        }
    }




}
