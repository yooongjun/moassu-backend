package com.ssu.moassubackend.scrap.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HomepageElecDto {

    private String admin;
    private String url;
    private String title;
    private String date;
    private String content;

    public HomepageElecDto() {}

    @Builder
    public HomepageElecDto(String admin, String url, String title, String date, String content) {
        this.admin = admin;
        this.url = url;
        this.title = title;
        this.date = date;
        this.content = content;
    }

}
