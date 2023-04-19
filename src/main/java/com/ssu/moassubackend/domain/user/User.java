package com.ssu.moassubackend.domain.user;

import com.ssu.moassubackend.domain.common.BaseEntity;
import com.ssu.moassubackend.domain.subscription.Subscription;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.bind.DefaultValue;

import java.util.ArrayList;
import java.util.List;


@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "users")
public class User extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    // Resource server의 사용자 식별자
    @Column(unique = true, nullable = false)
    private String oAuthId;

    @Column(unique = true)
    private String email;

    @Column(unique = true, nullable = false)
    private String nickName;

    @OneToMany(mappedBy = "user")
    private List<Subscription> subscriptions = new ArrayList<>();

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private Role role;

    @Builder
    public User(String oAuthId,String email, String nickName, Role role) {
        this.oAuthId = oAuthId;
        this.email = email;
        this.nickName = nickName;
        this.role = role;
    }

    public User update(String nickName, String email) {
        this.nickName = nickName;
        this.email = email;

        return this;
    }

    public String getRoleKey() {
        return this.role.getKey();
    }

    public User(String email, String nickName) {
        this.email = email;
        this.nickName = nickName;
    }
    // 이메일 수정 메서드 추가

    public User changeEmail(String email) {

        if (!email.isEmpty())
            this.email = email;

        return this;
    }
    // 이름 수정 메서드 추가

    public User changeName(String name) {

        if (!name.isEmpty()) {
            this.nickName = name;
        }

        return this;
    }


}
