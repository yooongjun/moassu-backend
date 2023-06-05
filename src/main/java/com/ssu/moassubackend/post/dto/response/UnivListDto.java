package com.ssu.moassubackend.post.dto.response;

import com.ssu.moassubackend.domain.post.Homepage;
import com.ssu.moassubackend.domain.post.Post;
import com.ssu.moassubackend.domain.post.Unipage;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
public class UnivListDto {
    private Long id;
    private String category;
    private String title;
    private LocalDate date;
    private String link;
    private String major;

    public UnivListDto() {}

    @Builder
    public UnivListDto(Long id, String category, String title, LocalDate date, String link, String major) {
        this.id = id;
        this.category = category;
        this.title = title;
        this.date = date;
        this.link = link;
        this.major = major;
    }


    public UnivListDto(Unipage page) {
        this.id = page.getId();
        this.category = page.getMajor();
        this.title = page.getTitle();
        this.date = page.getWriteDate();
        this.link = page.getSsu_link();
    }
    public UnivListDto(Homepage page) {
        this.id = page.getId();
        this.category = page.getField();
        this.title = page.getTitle();
        this.date = page.getWriteDate();
        this.link = page.getSsu_link();
        this.major = page.getMajor();
    }
}
