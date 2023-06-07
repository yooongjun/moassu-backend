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
import java.util.Iterator;
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
        Iterator<Subscription> iterator = userSubscriptions.iterator();

        while (iterator.hasNext()) {
            Subscription userSubscription = iterator.next();
            String keyword = userSubscription.getKeyword();

            // 기존에 있던 항목은 추가하지 않음
            if (subscriptions.contains(keyword)) {
                subscriptions.remove(keyword);
            } else {
                // 기존 구독과 일치하지 않는 항목 제거
                iterator.remove();
                subscriptionRepository.delete(userSubscription);
            }
        }

        // 새로운 항목 추가
        for (String subscription : subscriptions) {
            Subscription addSubscription = new Subscription(user, subscription);
            subscriptionRepository.save(addSubscription);
            userSubscriptions.add(addSubscription);
        }
    }



}
