package com.ssu.moassubackend.scrap.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class HomepageInstaDto {

    private String admin;
    private String url;
    private String title;
    private String content;
    private String date;

    public HomepageInstaDto() {}

    @Builder
    public HomepageInstaDto(String admin, String url, String title, String content, String date) {
        this.admin = admin;
        this.url = url;
        this.title = title;
        this.content = content;
        this.date = date;
    }

}
