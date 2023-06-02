package com.ssu.moassubackend.scrap.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Getter @Setter
public class HomepageFunDto {
    private String admin;
    private String url;
    private String category;
    private String title;
    private List<String> applyPeriod;
    private List<String> operatePeriod;
    private String cover;
    private List<String> tag;
    private String summary;
    private String content;
    private Map<String, String> attach;

    public HomepageFunDto() {}

    @Builder
    public HomepageFunDto(String admin, String url, String category, String title, String cover, String content,
                          Map<String, String> attach, List<String> applyPeriod, List<String> operatePeriod) {
        this.admin = admin;
        this.url = url;
        this.category = category;
        this.title = title;
        this.cover = cover;
        this.content = content;
        this.attach =attach;
        this.applyPeriod = applyPeriod;
        this.operatePeriod = operatePeriod;
    }

}
