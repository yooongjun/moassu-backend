package com.ssu.moassubackend.config.oauth.service;

import com.ssu.moassubackend.config.oauth.dto.CustomUserDetails;
import com.ssu.moassubackend.domain.user.User;
import com.ssu.moassubackend.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("[loadUserByUsername] username: {}", username);
        User user = userRepository.findByoAuthId(username).orElseThrow(() -> new RuntimeException("유저 정보가 존재하지 않습니다."));
        return new CustomUserDetails(username);
    }

}
