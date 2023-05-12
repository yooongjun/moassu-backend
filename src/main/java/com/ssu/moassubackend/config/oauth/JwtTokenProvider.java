package com.ssu.moassubackend.config.oauth;

import com.ssu.moassubackend.config.oauth.dto.CustomUserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    private final UserDetailsService userDetailsService;
    private final String JWT_TOKEN_HEADER = "m-auth-token";

    @Value(value = "${secret.jwt.key}")
    private String secretKey="secretKey";
    private final long tokenValidMillisecond = 1000 * 60 * 60;

    @PostConstruct
    protected void init() {
        log.info("[init]secret 초기화");
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    public String createToken(String userOAuthId) {
        log.info("[createToken] userOAuthId = {}", userOAuthId);
        Claims claims = Jwts.claims().setSubject(userOAuthId);
        Date now = new Date();

        String token = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + tokenValidMillisecond))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();

        return token;
    }

    public Authentication getAuthentication(String token) {
        log.info("[getAuthentication] 토큰 기반 정보 조회 시작");
        String userName = getUserName(token);
        UserDetails userDetails = new CustomUserDetails(userName);
        return new UsernamePasswordAuthenticationToken(userDetails, "");
    }

    public String resolveToken(HttpServletRequest request) {
        return request.getHeader(JWT_TOKEN_HEADER);
    }

    public boolean validateToken(String token) {
        log.info("[validateToken] 토큰 유효성 확인");
        try {
            Claims body = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
            return !body.getExpiration().before(new Date());
        } catch (Exception e) {
            log.error("[validateToken] 에러 발생");
            return false;
        }

    }

    public String getUserName(String token) {
        log.info("[getUserName] 토큰 기반 userName 추출");
        Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);

        return claims.getBody().getSubject();
    }

}
