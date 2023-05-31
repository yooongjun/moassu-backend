package com.ssu.moassubackend.post.service;

import com.ssu.moassubackend.domain.image.Image;
import com.ssu.moassubackend.domain.post.Homepage;
import com.ssu.moassubackend.domain.post.Post;
import com.ssu.moassubackend.domain.post.Unipage;
import com.ssu.moassubackend.post.dto.response.HomepageDetailDto;
import com.ssu.moassubackend.post.dto.response.UnivDetailDto;
import com.ssu.moassubackend.post.dto.response.UnivListDto;
import com.ssu.moassubackend.post.image.service.ImageService;
import com.ssu.moassubackend.post.repository.PostRepository;
import com.ssu.moassubackend.scrap.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class PostService {

    private final PostRepository postRepository;
    private final ImageService imageService;
    
    public List<UnivListDto> getUnivList(Pageable pageable) {
        List<Post> pages = postRepository.findAll();
        List<Unipage> unipages = new ArrayList<>();
        for (Post post : pages) {
            if(post instanceof Unipage) {
                Unipage unipage = (Unipage) post;
                unipages.add(unipage);
            }
        }

        List<UnivListDto> univListDtoList = unipages.stream()
                .map(page -> new UnivListDto(page))
                .collect(Collectors.toList());

        return univListDtoList;
    }



    public UnivDetailDto detailUnivPost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("detail exception"));

        if(post instanceof Unipage) {
            Unipage unipage = (Unipage) post;
            List<Image> images = unipage.getImages();
            List<String> urls = images.stream()
                    .map(img -> img.getImage_url())
                    .collect(Collectors.toList());

            UnivDetailDto univDetailDto = UnivDetailDto.builder()
                    .url(unipage.getSsu_link())
                    .category(unipage.getField())
                    .content(unipage.getContent())
                    .title(unipage.getTitle())
                    .attach(urls)
                    .build();

            return univDetailDto;
        }

        return null;
    }

    public List<UnivListDto> getDepartmentList(Pageable pageable) {
        List<Post> posts = postRepository.findAll();
        List<Homepage> homepages = new ArrayList<>();
        for (Post post : posts) {
            if(post instanceof Homepage) {
                Homepage homepage = (Homepage) post;
                homepages.add(homepage);
            }
        }

        List<UnivListDto> univListDtoList = homepages.stream()
                .map(page -> new UnivListDto(page))
                .collect(Collectors.toList());

        return univListDtoList;
    }


    public HomepageDetailDto getDepartmentPost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("detail exception"));
        if(post instanceof Homepage) {
            Homepage homepage = (Homepage) post;
            List<Image> images = homepage.getImages();

            List<String> urls = images.stream()
                    .map(img -> img.getImage_url())
                    .collect(Collectors.toList());

            HomepageDetailDto detailDto = HomepageDetailDto.builder()
                    .admin(homepage.getMajor())
                    .id(homepage.getId())
                    .url(homepage.getSsu_link())
                    .num(homepage.getField())
                    .content(homepage.getContent())
                    .date(homepage.getWrite_date().toString())
                    .attach(urls)
                    .build();

            return detailDto;
        }

        return null;
    }
    

    public void saveHomepageUniv(List<HomepageUnivDto> homepageUnivDtos) {
        for (HomepageUnivDto dto : homepageUnivDtos) {
            List<String> attachList = new ArrayList<>();

            // String -> LocalDate 변환
            String dateString = dto.getDate();
            LocalDate date = converToLocalDateComputer(dateString);

            // 새로운 Post 저장
            Post post = new Unipage(dto.getTitle(), dto.getContent(), date, dto.getCategory(), dto.getUrl());
            Post savedPost = postRepository.save(post);

            // 새로운 Post 의 이미지 리스트 저장
            if(dto.getAttach() != null && !dto.getAttach().isEmpty()) {
                Map<String, String> attach = dto.getAttach();
                for (Map.Entry<String, String> entry : attach.entrySet()) {
                    String key = entry.getKey();
                    String value = entry.getValue();
                    imageService.saveImage(savedPost, key, value);
                }
            }

        }
    }

    public void saveHomepageComputer(List<HomepageComDto> homepageComDtos) {
        for (HomepageComDto dto : homepageComDtos) {
            List<String> attachList = new ArrayList<>();

            // String -> LocalDate 변환
            String dateString = dto.getDate();
            LocalDate date = convertToLocalDateUniv(dateString);

            Post post = new Homepage(dto.getTitle(), dto.getContent(), date, dto.getNum(), dto.getUrl(), dto.getAdmin());
            Post savedPost = postRepository.save(post);

            // 새로운 Post 의 이미지 리스트 저장
            if(dto.getAttach() != null && !dto.getAttach().isEmpty()) {
                Map<String, String> attach = dto.getAttach();
                for (Map.Entry<String, String> entry : attach.entrySet()) {
                    String key = entry.getKey();
                    String value = entry.getValue();
                    imageService.saveImage(savedPost, key, value);
                }
            }

        }
    }
    public void saveHomepageElectronic(List<HomepageElecDto> homepageElecDtos) {
        for (HomepageElecDto dto : homepageElecDtos) {
            List<String> attachList = new ArrayList<>();

            // String -> LocalDate 변환
            String dateString = dto.getDate();
            LocalDate date = convertToLocalDateElec(dateString);

            Post post = new Homepage(dto.getTitle(), dto.getContent(), date, dto.getAdmin(), dto.getUrl());
            Post savedPost = postRepository.save(post);
        }
    }

    public void saveHomepageSoftware(List<HomepageSoftDto> homepageSoftDtos) {
        for (HomepageSoftDto dto : homepageSoftDtos) {
            List<String> attachList = new ArrayList<>();

            // String -> LocalDate 변환
            String dateString = dto.getDate();
            LocalDate date = convertToLocalDateSoft(dateString);

            Post post = new Homepage(dto.getTitle(), dto.getContent(), date, dto.getNum(), dto.getUrl(), dto.getAdmin());
            Post savedPost = postRepository.save(post);

            // 새로운 Post 의 이미지 리스트 저장
            if(dto.getAttach() != null && !dto.getAttach().isEmpty()) {
                Map<String, String> attach = dto.getAttach();
                for (Map.Entry<String, String> entry : attach.entrySet()) {
                    String key = entry.getKey();
                    String value = entry.getValue();
                    imageService.saveImage(savedPost, key, value);
                }
            }

        }
    }

    public void saveHomepageGm(List<HomepageGmDto> homepageGmDtos) {
        for (HomepageGmDto dto : homepageGmDtos) {
            List<String> attachList = new ArrayList<>();

            // String -> LocalDate 변환
            String dateString = dto.getDate();
            LocalDate date = convertToLocalDateSoft(dateString);

            Post post = new Homepage(dto.getTitle(), dto.getContent(), date, dto.getNum(), dto.getUrl(), dto.getAdmin());
            Post savedPost = postRepository.save(post);

            // 새로운 Post 의 이미지 리스트 저장
            if(dto.getAttach() != null && !dto.getAttach().isEmpty()) {
                Map<String, String> attach = dto.getAttach();
                for (Map.Entry<String, String> entry : attach.entrySet()) {
                    String key = entry.getKey();
                    String value = entry.getValue();
                    imageService.saveImage(savedPost, key, value);
                }
            }

        }
    }

    public void saveHomepageAI(List<HomepageAiDto> homepageAiDtos) {
        for (HomepageAiDto dto : homepageAiDtos) {
            List<String> attachList = new ArrayList<>();

            // String -> LocalDate 변환
            String dateString = dto.getDate();
            LocalDate date = convertToLocalDateUniv(dateString);

            Post post = new Homepage(dto.getTitle(), dto.getContent(), date, dto.getAdmin(), dto.getUrl());
            Post savedPost = postRepository.save(post);
        }
    }

    public LocalDate converToLocalDateComputer(String dateString) {
        // 날짜 형식 지정
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(" yyyy년 M월 d일", Locale.KOREAN);

        // 문자열을 LocalDate로 변환
        LocalDate date = LocalDate.parse(dateString, formatter);

        return date;
    }


    public LocalDate convertToLocalDateUniv(String dateString) {
        // 날짜 형식 지정
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");

        // 문자열을 LocalDate로 변환
        LocalDate date = LocalDate.parse(dateString, formatter);

        return date;
    }

    public LocalDate convertToLocalDateElec(String dateString) {
        // 날짜 형식 지정
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy. MM. dd");

        // 문자열을 LocalDate로 변환
        LocalDate date = LocalDate.parse(dateString, formatter);

        return date;
    }
    public LocalDate convertToLocalDateSoft(String dateString) {
        // 날짜 형식 지정
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        // 문자열을 LocalDate로 변환
        LocalDate date = LocalDate.parse(dateString, formatter);

        return date;
    }

}

