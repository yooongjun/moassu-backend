package com.ssu.moassubackend.post.service;

import com.ssu.moassubackend.domain.post.Post;
import com.ssu.moassubackend.domain.post.Unipage;
import com.ssu.moassubackend.post.repository.PostRepository;
import com.ssu.moassubackend.scrap.dto.HomepageUnivDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional
public class PostService {

    private final PostRepository postRepository;

    public void saveHomepageUniv(List<HomepageUnivDto> homepageUnivDtos) {
        for (HomepageUnivDto dto : homepageUnivDtos) {
            String thumbAttach;
            List<String> attachList = new ArrayList<>();

            // String -> LocalDate 변환
            String dateString = dto.getDate();
            LocalDate date = convertToLocalDate(dateString);

            // Map -> List 변환
            if(dto.getAttach() != null && !dto.getAttach().isEmpty()) {
                Map<String, String> attach = dto.getAttach();
                for (String value : attach.values()) {
                    attachList.add(value);
                }
            }

            if(attachList != null && !attachList.isEmpty()) {
                thumbAttach = attachList.get(0);
            }

            Post post = new Unipage(dto.getTitle(), dto.getContent(), date, dto.getCategory(), dto.getUrl(), dto.getUrl());
            postRepository.save(post);

        }
    }

    public LocalDate convertToLocalDate(String dateString) {
        // 날짜 형식 지정
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(" yyyy년 M월 d일", Locale.KOREAN);

        // 문자열을 LocalDate로 변환
        LocalDate date = LocalDate.parse(dateString, formatter);

        return date;
    }

}

