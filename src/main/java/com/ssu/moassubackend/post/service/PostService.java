package com.ssu.moassubackend.post.service;

import com.ssu.moassubackend.domain.post.Post;
import com.ssu.moassubackend.domain.post.Unipage;
import com.ssu.moassubackend.scrap.dto.HomepageUnivDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
@Transactional
public class PostService {

    public void saveHomepageUniv(List<HomepageUnivDto> homepageUnivDtos) {
        for (HomepageUnivDto dto : homepageUnivDtos) {
            String dateString = dto.getDate();
            LocalDate date = convertToLocalDate(dateString);

//            Post post = new Unipage(dto.getTitle(), dto.getContent(), );

        }
    }

    public LocalDate convertToLocalDate(String dateString) {
        // 날짜 형식 지정
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy년 M월 d일", Locale.KOREAN);

        // 문자열을 LocalDate로 변환
        LocalDate date = LocalDate.parse(dateString, formatter);
        System.out.println("date = " + date);

        return date;
    }

}

