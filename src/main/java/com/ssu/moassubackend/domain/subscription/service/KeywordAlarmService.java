package com.ssu.moassubackend.domain.subscription.service;

import com.ssu.moassubackend.domain.mail.service.MailService;
import com.ssu.moassubackend.domain.post.Status;
import com.ssu.moassubackend.domain.subscription.Subscription;
import com.ssu.moassubackend.domain.subscription.dto.SearchParameter;
import com.ssu.moassubackend.domain.subscription.repository.SubscriptionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@RequiredArgsConstructor
@Service
public class KeywordAlarmService {

    private final SubscriptionRepository subscriptionRepository;

    private final MailService mailService;

    public Map getKeywordMap(List<Subscription> subscriptionList) {

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();

        for (Subscription subscription : subscriptionList) {
            String email = subscription.getUser().getEmail();
            String keyword = subscription.getKeyword();

            map.add(keyword, email);
        }

        return map;
    }

    public void doAlarm(SearchParameter parameter) {
        List<Subscription> subscriptions = subscriptionRepository.findAll();

        MultiValueMap<String, String> keywordMap = (MultiValueMap<String, String>) getKeywordMap(subscriptions);

        Set<String> keywordSet = keywordMap.keySet();
        Map<String, String> mailMap = new ConcurrentHashMap<String, String>();

        for (String keyword : keywordSet) {
            // 현재 글이 키워드를 포함하고 있는 경우
            // 현재 키워드를 구독한 모든 메일을 보낼 메일 맵에 추가
            if (!keyword.isEmpty() && isContainsKeyword(parameter, keyword) )
            {
                keywordMap.get(keyword).forEach(email -> mailMap.put(email, keyword));
            }
        }

        mailMap.keySet().forEach(email -> mailService.sendMail(mailMap.get(email), email));
    }

    public boolean isContainsKeyword(SearchParameter parameter, String keyword) {
        // 조사할 파라미터들
        String content = parameter.getContent();
        String major = parameter.getMajor();
        String title = parameter.getTitle();
        keyword = keyword.toLowerCase();

        if (content.toLowerCase().contains(keyword) || major.toLowerCase().contains(keyword) || title.toLowerCase().contains(keyword))
        {
            return  true;
        }

        return false;
    }



}
