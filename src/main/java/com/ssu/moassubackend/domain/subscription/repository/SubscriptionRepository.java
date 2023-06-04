package com.ssu.moassubackend.domain.subscription.repository;

import com.ssu.moassubackend.domain.subscription.Subscription;
import com.ssu.moassubackend.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

    List<Subscription> findByUser(User user);

}
