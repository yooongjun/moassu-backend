package com.ssu.moassubackend.post.repository;

import com.ssu.moassubackend.domain.post.Homepage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HomepageRepository extends JpaRepository<Homepage, Long> {
}
