package com.ssu.moassubackend.scrap.dto;

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
}
