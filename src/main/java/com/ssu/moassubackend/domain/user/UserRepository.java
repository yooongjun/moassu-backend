package com.ssu.moassubackend.domain.user;

import org.hibernate.annotations.Fetch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByoAuthId(String oAuthId);

    @Query("select u from User u LEFT join fetch u.subscriptions s where u.email = :email")
    Optional<User> findByEmail(@Param("email") String email);

}
