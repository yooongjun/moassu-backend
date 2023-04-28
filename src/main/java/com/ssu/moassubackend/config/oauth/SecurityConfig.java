package com.ssu.moassubackend.config.oauth;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.web.SecurityFilterChain;

@Configuration // Spring security 6.0 부터 @EnableWebSecurity에 @Configuration 이 빠져있으므로 추가
@EnableWebSecurity // spring security 설정 활성화 시키는 어노테이션
@RequiredArgsConstructor
public class SecurityConfig{

    @Autowired
    private final OAuth2UserService customOAuth2UserService;

    // HttpSecurity 설정
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .httpBasic().disable()  // UI 사용하는 것을 기본값으로 가진 시큐리티 설정 비활성화
                .csrf().disable()       // CSRF 보안 비활성화

                .sessionManagement()    // 세션 관리 정책 설정
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)     // JWT로 인증, 인증에서 세션은 사용하지 않음

                // 요청에 대한 권한 확인 설정
                // 개인 페이지 조회, 쪽지 페이지에 대한 권한 관리 추가 필요
                .and()
                .authorizeHttpRequests()
                .requestMatchers("/security")
                .hasRole("USER")

                /**
                 예외 처리 설정 필요
                 .and()
                 .exceptionHandling().accessDeniedHandler(new CustomAccessDeniedHandler() // 권한 확인 예외 처리
                 .and()
                 .exceptionHandling().authenticationEntryPoint(new CustomAuthenticationEntryPoint()) // 인증 예외 처리
                 **/

                // OAuth2 로그인 설정 시작지점
                .and()
                .oauth2Login()
                .userInfoEndpoint().userService(customOAuth2UserService);

//                .and()
//                .successHandler(configSuccessHandler()) // 인증 성공 처리 구현 예정
//                .failureHandler(configFailureHandler()) // 인증 실패 처리 구현 예정
//                .permitAll();

        return http.build();
    }

    // WebSecurity 설정
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers("**");
    }


}
