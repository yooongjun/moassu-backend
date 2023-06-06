package com.ssu.moassubackend.domain.subscription.controller;

import com.ssu.moassubackend.domain.subscription.dto.SubscribeRequestDto;
import com.ssu.moassubackend.domain.subscription.dto.SubscribeResponseDto;
import com.ssu.moassubackend.domain.subscription.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/subscriptions")
@RestController
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    @GetMapping
    public ResponseEntity getSubscriptions(@RequestParam(name = "email") String email) {

        SubscribeResponseDto subscriptions = subscriptionService.getSubscriptions(email);

        return ResponseEntity.ok(subscriptions);
    }

    @PostMapping
    public ResponseEntity saveSubscriptions(@RequestBody SubscribeRequestDto subscribeRequestDto) {
        subscriptionService.addSubscribe(subscribeRequestDto.getKeywordList(), subscribeRequestDto.getEmail());

        return ResponseEntity.ok("Subscribed successfully.");
    }

}
