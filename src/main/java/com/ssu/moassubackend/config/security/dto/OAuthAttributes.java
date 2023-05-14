package com.ssu.moassubackend.config.security.dto;

import com.ssu.moassubackend.config.security.SocialType;
import com.ssu.moassubackend.domain.user.Role;
import com.ssu.moassubackend.domain.user.User;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

/**
 * OAuth2에서 넘어온 정보를 담는 DTO
 */
@Getter
public class OAuthAttributes {
    private Map<String, Object> attributes;
    private String oAuthId;     // OAuth2.0에서 사용하는 PK
    private String nickName;    // 닉네임 정보
    private String email;       // 이메일 주소
    private SocialType socialType;

    @Builder
    public OAuthAttributes(Map<String, Object> attributes, String oAuthId, String nickName, String email, SocialType socialType) {
        this.attributes = attributes;
        this.oAuthId = oAuthId;
        this.nickName = nickName;
        this.email = email;
        this.socialType = socialType;
    }

    public static OAuthAttributes of(SocialType socialType, String userNameAttributeName, Map<String, Object> attributes) {

        if (socialType.equals(SocialType.KAKAO)) {
            return ofKakao("id", attributes);
        }

        return ofGoogle(userNameAttributeName, attributes);
    }

    private static OAuthAttributes ofGoogle(String userNameAttributeName, Map<String, Object> attributes) {

        String nickName = (String) attributes.get("name");
        String oAuthId = (String) attributes.get(userNameAttributeName);
        String email = (String) attributes.get("email");

        return OAuthAttributes.builder()
                .oAuthId(oAuthId)
                .email(email)
                .nickName(nickName)
                .attributes(attributes)
                .socialType(SocialType.GOOGLE)
                .build();
    }


    private static OAuthAttributes ofKakao(String userNameAttributeName, Map<String, Object> attributes) {

        Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
        Map<String, Object> profile = (Map<String, Object>) kakaoAccount.get("profile");

        String nickname = (String) profile.get("nickname");
        String email = (String) kakaoAccount.get("email");
        String oAuthId = String.valueOf(attributes.get(userNameAttributeName));

        return OAuthAttributes.builder()
                .oAuthId(oAuthId)
                .email(email)
                .nickName(nickname)
                .attributes(attributes)
                .socialType(SocialType.KAKAO)
                .build();
    }

    // DTO(OAuthAttributes)를 엔티티로 변환하는 메서드
    public User toEntity() {

        return User.builder()
                .oAuthId(this.getOAuthId())
                .email(this.getEmail())
                .nickName(this.getNickName())
                .role(Role.USER)
                .socialType(this.getSocialType())
                .build();
    }

}
