package com.ssu.moassubackend.config.security.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssu.moassubackend.config.security.SocialType;
import com.ssu.moassubackend.config.security.dto.OAuthAttributes;
import com.ssu.moassubackend.config.security.dto.OauthToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class OAuth2TokenService {

    @Value("${secret.kakao.client.id}")
    private String KAKAO_REST_API_KEY;

    @Value("${secret.kakao.redirect.uri}")
    private String KAKAO_REDIRECT_URI;

    // authorization code로 access token을 발급받고, 사용자 정보를 반환하는 메서드
    public OAuthAttributes getUserInfo(String authorizationCode, SocialType socialType) {

        if (socialType.equals(SocialType.KAKAO)) {

            // get access token
            OauthToken oauthToken = getKakaoAccessToken(authorizationCode);

            // get User Information
            OAuthAttributes oAuthAttributes = loadKakao(oauthToken.getAccess_token(), oauthToken.getRefresh_token());
            return oAuthAttributes;

        }

        return null;
    }

    // KAKAO REST API로 access token을
    public OauthToken getKakaoAccessToken(String authorizationCode)  {

        // Set URI
        URI uri = UriComponentsBuilder
                .fromUriString("https://kauth.kakao.com")
                .path("/oauth/token")
                .encode()
                .build()
                .toUri();

        RestTemplate restTemplate = new RestTemplate();

        // Set header
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.add("Accept", "application/json");

        // Set parameter
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", KAKAO_REST_API_KEY);
        params.add("redirect_uri", KAKAO_REDIRECT_URI);
        params.add("code", authorizationCode);

        // Set http entity
        RequestEntity<MultiValueMap<String, String>> requestEntity = RequestEntity.post(uri).headers(headers).body(params);
        ResponseEntity<String> responseEntity;

        try {
            // 토큰 받기
            responseEntity = restTemplate.exchange(requestEntity, String.class);
        } catch (Exception e) {
            log.error("[kakao] access token 발급 실패 ");
            throw (new RuntimeException("authorization code가 잘못되었습니다."));
        }

        // JSON String to OauthToken
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        OauthToken oauthToken;

        try {
            oauthToken = objectMapper.readValue(responseEntity.getBody(), OauthToken.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return oauthToken;
    }

    public OAuthAttributes loadKakao(String accessToken, String refreshToken) {

        RestTemplate restTemplate = new RestTemplate();

        // Set URI
        URI uri = UriComponentsBuilder
                .fromUriString("https://kapi.kakao.com")
                .path("/v2/user/me")
                .encode()
                .build()
                .toUri();

        // Set headers
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Authorization", "Bearer " + accessToken);

        // Set http entity
        RequestEntity<MultiValueMap<String, String>> requestEntity = new RequestEntity<>(headers, HttpMethod.GET, uri);

        // 유저 정보 불러오기
        ResponseEntity<String> responseEntity = restTemplate.exchange(requestEntity, String.class);

        // JSON String to Object
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> attributes;

        try {
            attributes = objectMapper.readValue(responseEntity.getBody(), HashMap.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return OAuthAttributes.of(SocialType.KAKAO, "", attributes);
    }

}
