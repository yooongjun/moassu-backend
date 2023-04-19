package com.ssu.moassubackend.config.oauth;

import com.ssu.moassubackend.config.oauth.dto.OAuthAttributes;
import com.ssu.moassubackend.domain.user.User;
import com.ssu.moassubackend.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    @Autowired
    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService delegate = new DefaultOAuth2UserService();

        OAuth2User oAuth2User = delegate.loadUser(userRequest);         // OAuth2를 통해 불러온 유저 정보

        String registrationId = userRequest.getClientRegistration().getRegistrationId();    // 현재 로그인 진행 중인 서비스 구분

        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails()
                .getUserInfoEndpoint().getUserNameAttributeName();      // OAuth2 로그인 진행 시 키가 되는 필드값, 구글은 지원, 카카오는 x

        Map<String, Object> attributes = oAuth2User.getAttributes();

        OAuthAttributes oAuthAttributes = OAuthAttributes.of(registrationId, userNameAttributeName, attributes);

        User user = saveOrUpdate(oAuthAttributes);  // 유저 정보 갱신 로직

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(user.getRoleKey())),
                        oAuthAttributes.getAttributes(),
                        oAuthAttributes.getNameAttributeKey()
        );
    }

    private User saveOrUpdate(OAuthAttributes oAuthAttributes) {
        User user = userRepository.findByoAuthId(oAuthAttributes.getOAuthId())
                .map(entity -> entity.update(oAuthAttributes.getNickName(), oAuthAttributes.getEmail())) // 계정이 존재하는 경우 update
                .orElse(oAuthAttributes.toEntity(oAuthAttributes));  // 계정이 없는 경우 User 객체를 생성하여 DB에 저장

        return userRepository.save(user);
    }

}
