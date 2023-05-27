package com.ssu.moassubackend.post.dto.response;

import com.ssu.moassubackend.domain.image.Image;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class HomepageDetailDto {

    private Long id;
    private String admin;
    private String url;
    private String num;
    private String title;
    private String date;
    private String content;
    private List<String> attach;

    @Builder
    public HomepageDetailDto(Long id, String admin, String url, String num, String title, String date,
                             String content, List<String> attach) {
        this.id = id;
        this.admin = admin;
        this.url = url;
        this.num = num;
        this.title = title;
        this.date = date;
        this.content = content;
        this.attach = attach;
    }



}
