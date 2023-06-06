package com.ssu.moassubackend.domain.subscription;

import com.ssu.moassubackend.domain.department.Department;
import com.ssu.moassubackend.domain.user.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String keyword;

    @Builder
    public Subscription(User user, String keyword) {
        this.user = user;
        this.keyword = keyword;
    }

}