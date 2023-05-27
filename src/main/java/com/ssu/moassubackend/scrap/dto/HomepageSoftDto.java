package com.ssu.moassubackend.scrap.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class HomepageSoftDto {

    private String admin;
    private String url;
    private String num;
    private String title;
    private String date;
    private String content;
    private Map<String, String> attach;

    public HomepageSoftDto() {}

    @Builder
    public HomepageSoftDto(String admin, String url, String title, String num, String date, String content,
                          Map<String, String> attach) {
        this.admin = admin;
        this.url = url;
        this.title = title;
        this.num = num;
        this.date = date;
        this.content = content;
        this.attach = attach;
    }

}
