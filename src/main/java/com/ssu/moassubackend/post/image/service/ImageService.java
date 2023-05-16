package com.ssu.moassubackend.post.image.service;

import com.ssu.moassubackend.domain.image.Image;
import com.ssu.moassubackend.domain.post.Post;
import com.ssu.moassubackend.post.image.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ImageService {

    private final ImageRepository imageRepository;

    public void saveImage(Post post, String image_name, String image_url) {

        Image image = Image.builder()
                .image_name(image_name)
                .image_url(image_url)
                .build();

        image.changeRelationWithPost(post);

        imageRepository.save(image);

    }

}
