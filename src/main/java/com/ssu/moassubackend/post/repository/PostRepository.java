package com.ssu.moassubackend.post.repository;

import com.ssu.moassubackend.domain.post.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {
//    Optional<Post> findByTitle(String title);
    List<Post> findByTitle(String title);
}
