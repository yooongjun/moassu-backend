package com.ssu.moassubackend.post.image.repository;

import com.ssu.moassubackend.domain.image.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
