package com.ssu.moassubackend.scrap.controller;

import com.ssu.moassubackend.domain.post.Post;
import com.ssu.moassubackend.domain.post.Unipage;
import com.ssu.moassubackend.post.repository.PostRepository;
import com.ssu.moassubackend.post.service.PostService;
import com.ssu.moassubackend.scrap.dto.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@RestController
public class ScrapController {

    private final PostRepository postRepository;
    private final PostService postService;

    @PostMapping("/savedata/univ")
    public void savedata(@RequestBody HomepageUnivDto[] homepageUnivDtos) {

        List<HomepageUnivDto> homepageUnivDtoList = new ArrayList<>();

        for (HomepageUnivDto dto : homepageUnivDtos) {

            Optional<Post> postByTitle = postRepository.findByTitle(dto.getTitle());
            if (postByTitle.isPresent()) break;

            String url = dto.getUrl();
            String title = dto.getTitle();
            String admin = dto.getAdmin();
            String date = dto.getDate();
            String content = dto.getContent();
            String category = dto.getCategory();
            Map<String, String> attach = dto.getAttach();

            for (Map.Entry<String, String> att : attach.entrySet()) {
                String key = att.getKey();
                String value = att.getValue();
            }

            HomepageUnivDto univDto = HomepageUnivDto.builder()
                    .title(dto.getTitle())
                    .content(dto.getContent())
                    .category(dto.getCategory())
                    .admin(dto.getAdmin())
                    .content(dto.getContent())
                    .attach(dto.getAttach())
                    .url(dto.getUrl())
                    .date(dto.getDate())
                    .build();

            homepageUnivDtoList.add(univDto);

            System.out.println("================================");
        }

        postService.saveHomepageUniv(homepageUnivDtoList);

    }

    @PostMapping("/savedata/comuter")
    public void savedatacom(@RequestBody HomepageComDto[] homepageComDtos) {

        List<HomepageComDto> homepageComDtoList = new ArrayList<>();

        for (HomepageComDto dto : homepageComDtos) {

            Optional<Post> postByTitle = postRepository.findByTitle(dto.getTitle());
            if (postByTitle.isPresent()) break;

            String admin = dto.getAdmin();
            String url = dto.getUrl();
            String num = dto.getNum();
            String date = dto.getDate();
            String content = dto.getContent();
            Map<String, String> attach = dto.getAttach();

            for (Map.Entry<String, String> att : attach.entrySet()) {
                String key = att.getKey();
                String value = att.getValue();
            }

            HomepageComDto comDto = HomepageComDto.builder()
                    .admin(dto.getAdmin())
                    .url(dto.getUrl())
                    .content(dto.getContent())
                    .num(dto.getNum())
                    .date(dto.getDate())
                    .attach(dto.getAttach())
                    .title(dto.getTitle())
                    .build();

            homepageComDtoList.add(comDto);

            System.out.println("================================");

        }

        postService.saveHomepageComputer(homepageComDtoList);
    }

    @PostMapping("/savedata/electronic")
    public void savedataelec(@RequestBody HomepageElecDto[] homepageElecDtos) {

        List<HomepageElecDto> homepageElecDtoList = new ArrayList<>();

        for (HomepageElecDto dto : homepageElecDtos) {
            Optional<Post> postByTitle = postRepository.findByTitle(dto.getTitle());
            if (postByTitle.isPresent()) break;

            HomepageElecDto elecDto = HomepageElecDto.builder()
                    .admin(dto.getAdmin())
                    .title(dto.getTitle())
                    .url(dto.getUrl())
                    .content(dto.getContent())
                    .date(dto.getDate())
                    .build();

            homepageElecDtoList.add(elecDto);

        }

        postService.saveHomepageElectronic(homepageElecDtoList);

    }

    @PostMapping("/savedata/software")
    public void savedatasoft(@RequestBody HomepageSoftDto[] homepageSoftDtos) {

        List<HomepageSoftDto> homepageSoftDtoList = new ArrayList<>();

        for (HomepageSoftDto dto : homepageSoftDtos) {
            Optional<Post> postByTitle = postRepository.findByTitle(dto.getTitle());
            if (postByTitle.isPresent()) break;

            HomepageSoftDto softDto = HomepageSoftDto.builder()
                    .admin(dto.getAdmin())
                    .title(dto.getTitle())
                    .content(dto.getContent())
                    .url(dto.getUrl())
                    .date(dto.getDate())
                    .attach(dto.getAttach())
                    .num(dto.getNum())
                    .build();

            homepageSoftDtoList.add(softDto);

        }

        postService.saveHomepageSoftware(homepageSoftDtoList);

    }

    @PostMapping("/savedata/gm")
    public void savedatagm(@RequestBody HomepageGmDto[] homepageGmDtos) {

        List<HomepageGmDto> homepageGmDtoList = new ArrayList<>();

        for (HomepageGmDto dto : homepageGmDtos) {

            Optional<Post> postByTitle = postRepository.findByTitle(dto.getTitle());
            if (postByTitle.isPresent()) break;


            HomepageGmDto gmDto = HomepageGmDto.builder()
                    .admin(dto.getAdmin())
                    .url(dto.getUrl())
                    .content(dto.getContent())
                    .num(dto.getNum())
                    .date(dto.getDate())
                    .attach(dto.getAttach())
                    .title(dto.getTitle())
                    .build();

            homepageGmDtoList.add(gmDto);
        }

        postService.saveHomepageGm(homepageGmDtoList);
    }

    @PostMapping("/savedata/ai")
    public void savedataelec(@RequestBody HomepageAiDto[] homepageAiDtos) {

        List<HomepageAiDto> homepageAiDtoList = new ArrayList<>();

        for (HomepageAiDto dto : homepageAiDtos) {
            Optional<Post> postByTitle = postRepository.findByTitle(dto.getTitle());
            if (postByTitle.isPresent()) break;

            HomepageAiDto aiDto = HomepageAiDto.builder()
                    .admin(dto.getAdmin())
                    .url(dto.getUrl())
                    .title(dto.getTitle())
                    .date(dto.getDate())
                    .content(dto.getContent())
                    .build();

            homepageAiDtoList.add(aiDto);

        }

        postService.saveHomepageAI(homepageAiDtoList);
    }


}
