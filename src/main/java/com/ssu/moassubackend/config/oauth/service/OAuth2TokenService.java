package com.ssu.moassubackend.config.oauth.service;

import com.ssu.moassubackend.config.oauth.SocialType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Slf4j
@Service
public class OAuth2TokenService {

    @Value("${secret.kakao.client.id}")
    private String KAKAO_REST_API_KEY;

    @Value("${secret.kakao.redirect.uri}")
    private String KAKAO_REDIRECT_URI;

    public String getAccessToken(String authorizationCode, SocialType socialType) {

        if (authorizationCode != null && socialType.equals(SocialType.KAKAO)) {
            return getKakaoAccessToken(authorizationCode);
        }

        return null;
    }


    public String getKakaoAccessToken(String authorizationCode) {
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

        ResponseEntity<String> responseEntity = restTemplate.exchange(requestEntity, String.class);

        return responseEntity.getBody();
    }

}
