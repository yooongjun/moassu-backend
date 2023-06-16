package com.ssu.moassubackend.post.dto.response;

import com.ssu.moassubackend.domain.post.Instagram;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
public class InstaListDto {
    private Long id;
    private String admin;
    private String url;
    private String title;
    private LocalDate date;

    @Builder
    public InstaListDto(Long id, String admin, String url, String title, LocalDate date) {
        this.id = id;
        this.admin = admin;
        this.url = url;
        this.title = title;
        this.date = date;
    }

    public InstaListDto(Instagram page) {
        this.id = page.getId();
        this.admin = page.getMajor();
        this.url = page.getUrl();
        this.title = page.getTitle();
        this.date = page.getWriteDate();
    }

}
