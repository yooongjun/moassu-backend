package com.ssu.moassubackend.post.dto.response;

import com.ssu.moassubackend.domain.post.Instagram;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class InstaListDto {
    private Long id;
    private String admin;
    private String url;
    private String img;

    @Builder
    public InstaListDto(Long id, String admin, String url, String img) {
        this.id = id;
        this.admin = admin;
        this.url = url;
        this.img = img;
    }

    public InstaListDto(Instagram page) {
        this.id = page.getId();
        this.admin = page.getMajor();
        this.url = page.getUrl();
        this.img = page.getThumbnail();
    }

}
