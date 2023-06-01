package com.ssu.moassubackend.post.repository;

import com.ssu.moassubackend.domain.post.Instagram;
import com.ssu.moassubackend.domain.post.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InstagramRepository extends JpaRepository<Instagram, Long> {
    Optional<Instagram> findByUrl(String url);
}
