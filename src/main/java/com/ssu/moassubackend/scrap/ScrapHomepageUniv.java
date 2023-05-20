package com.ssu.moassubackend.scrap;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssu.moassubackend.domain.post.Post;
import com.ssu.moassubackend.post.repository.PostRepository;
import com.ssu.moassubackend.post.service.PostService;
import com.ssu.moassubackend.scrap.dto.HomepageUnivDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@RequiredArgsConstructor
@Component
public class ScrapHomepageUniv {

    @Value("classpath:ssufilelist/homepage_univ.json")
    private Resource jsonData;

    private final PostService postService;
    private final PostRepository postRepository;

//    @Scheduled(fixedDelay = 3600000)
//    @Scheduled(fixedDelay = 60000)
    public void getData() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        InputStream inputStream = jsonData.getInputStream();
        HomepageUnivDto[] homepageUnivDtos = objectMapper.readValue(inputStream, HomepageUnivDto[].class);
        List<HomepageUnivDto> homepageUnivList = Arrays.asList(homepageUnivDtos);

        List<HomepageUnivDto> saveList = new ArrayList<>();

        for (HomepageUnivDto homepageUnivDto : homepageUnivList) {

            Optional<Post> optPost = postRepository.findByTitle(homepageUnivDto.getTitle());
            if(optPost.isPresent()) break; // 이미 있는 Post 이면 그만 검사

            HomepageUnivDto univDto = HomepageUnivDto.builder()
                    .admin(homepageUnivDto.getAdmin())
                    .url(homepageUnivDto.getUrl())
                    .title(homepageUnivDto.getTitle())
                    .category(homepageUnivDto.getCategory())
                    .date(homepageUnivDto.getDate())
                    .content(homepageUnivDto.getContent())
                    .attach(homepageUnivDto.getAttach())
                    .build();

            saveList.add(univDto);

        }

        if(saveList != null && !saveList.isEmpty()) {
            postService.saveHomepageUniv(saveList); // 새로 저장할 Post 가 있으면 저장
        }
        else {
//            System.out.println("저장할 post가 없네");
        }


    }

}
