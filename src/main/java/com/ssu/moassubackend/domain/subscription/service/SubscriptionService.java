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
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;
    private final UserRepository userRepository;

    public SubscribeResponseDto getSubscriptions(String email) {

        User user = userRepository.findByEmail(email).orElseThrow();
        List<Subscription> subscriptionList = subscriptionRepository.findByUser(user);

        List<Department> departments = new ArrayList<>();
        subscriptionList.forEach(subscription -> departments.add(subscription.getDepartment()));

        return new SubscribeResponseDto(departments);
    }


    public void addSubscribe(List<String> subscriptions, String email) {

        // 이메일 정보 입력하지 않은 경우 구독 불가
        if (subscriptions.isEmpty() || email.isEmpty()) return;

        // 유저 조회
        User user = userRepository.findByEmail(email).orElseThrow();
        Long id = user.getId();

        // 유저의 구독 리스트 조회
        List<Subscription> subscriptionList = subscriptionRepository.findByUser(user);

        for (String s : subscriptions) {
            Department department = Department.valueOf(s.toUpperCase());

            if (!subscriptionList.contains(department)) {
                subscriptionRepository.save(new Subscription(user, department));
            }
        }
    }




}
