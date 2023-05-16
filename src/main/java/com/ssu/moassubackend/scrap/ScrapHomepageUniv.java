package com.ssu.moassubackend.scrap;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
@RequiredArgsConstructor
@Component
public class ScrapHomepageUniv {

    @Value("classpath:ssufilelist/homepage_univ.json")
    private Resource jsonData;

    private final PostService postService;

    //    @Scheduled(fixedDelay = 60000)
    @Scheduled(fixedDelay = 3600000)
    public void getData() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        InputStream inputStream = jsonData.getInputStream();
        HomepageUnivDto[] homepageUnivDtos = objectMapper.readValue(inputStream, HomepageUnivDto[].class);
        List<HomepageUnivDto> homepageUnivList = Arrays.asList(homepageUnivDtos);

        List<HomepageUnivDto> saveList = new ArrayList<>();

        for (HomepageUnivDto homepageUnivDto : homepageUnivList) {
            String admin = homepageUnivDto.getAdmin();
            String url = homepageUnivDto.getUrl();
            String title = homepageUnivDto.getTitle();
            String category = homepageUnivDto.getCategory();
            String date = homepageUnivDto.getDate();
            String content = homepageUnivDto.getContent();
            Map<String, String> attach = homepageUnivDto.getAttach();
            List<String> attachList = new ArrayList<>();
            for (String value : attach.values()) {
                attachList.add(value);
            }

        }
    }

}
