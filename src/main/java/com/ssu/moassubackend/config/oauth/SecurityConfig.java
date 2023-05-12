package com.ssu.moassubackend.config.oauth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebSecurity
@EnableWebMvc
public class SecurityConfig{

    private final JwtTokenProvider jwtTokenProvider;
    @Autowired
    public SecurityConfig(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }


    // HttpSecurity 설정
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()  // UI 사용하는 것을 기본값으로 가진 시큐리티 설정 비활성화
                .formLogin().disable()

                .csrf().disable()       // CSRF 보안 비활성화

                .sessionManagement()    // 세션 관리 정책 설정
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)     // JWT로 인증, 인증에서 세션은 사용하지 않음

                .and()

                .authorizeHttpRequests()
                .requestMatchers("/user/detail/**")
                .hasRole("USER")

                .anyRequest()
                .permitAll()

                .and()
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }



    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web -> web.ignoring()
                .requestMatchers("v2/api-docs", "/swagger-resources/**", "/swagger-ui.html",
                        "/webjars/**", "/swagger/**"));
    }

}
