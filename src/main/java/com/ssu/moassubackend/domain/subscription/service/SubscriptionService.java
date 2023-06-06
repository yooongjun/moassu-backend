package com.ssu.moassubackend.domain.subscription.service;

import com.ssu.moassubackend.domain.department.Department;
import com.ssu.moassubackend.domain.subscription.Subscription;
import com.ssu.moassubackend.domain.subscription.dto.SubscribeResponseDto;
import com.ssu.moassubackend.domain.subscription.repository.SubscriptionRepository;
import com.ssu.moassubackend.domain.user.User;
import com.ssu.moassubackend.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;
    private final UserRepository userRepository;

    public SubscribeResponseDto getSubscriptions(String email) {

        User user = userRepository.findByEmail(email).orElseThrow();
        List<Subscription> userSubscriptions = user.getSubscriptions();

        return new SubscribeResponseDto(userSubscriptions.stream()
                .map(subscription -> subscription.getKeyword())
                .collect(Collectors.toList()));
    }


    public void addSubscribe(List<String> subscriptions, String email) {

        // 이메일 정보 입력하지 않은 경우 구독 불가
        if (subscriptions.isEmpty() || email.isEmpty()) return;

        // 유저 조회
        log.info("email : {}", email);
        User user = userRepository.findByEmail(email).orElseThrow();

        // 유저의 구독 리스트 조회
        List<Subscription> userSubscriptions = user.getSubscriptions();

        // 입력한 Subscriptions 중
        for (String s : subscriptions) {

            if(userSubscriptions.stream().anyMatch(subscription -> subscription.getKeyword().equalsIgnoreCase(s)))
                continue;
            Subscription subscription = new Subscription(user, s);
            subscriptionRepository.save(subscription);
            userSubscriptions.add(subscription);
        }
    }



}
