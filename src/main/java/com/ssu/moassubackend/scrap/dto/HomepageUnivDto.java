package com.ssu.moassubackend.scrap.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter @Setter
public class HomepageUnivDto {
    private String admin;
    private String url;
    private String title;
    private String category;
    private String date;
    private String content;
    private Map<String, String> attach;

    public HomepageUnivDto() {}

    @Builder
    public HomepageUnivDto(String admin, String url, String title, String category, String date, String content,
                           Map<String, String> attach) {
        this.admin = admin;
        this.url = url;
        this.title = title;
        this.category = category;
        this.date = date;
        this.content = content;
        this.attach = attach;
    }

}
