package com.ssu.moassubackend.scrap.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class HomepageInstaDto {

    private String admin;
    private String url;
    private String img;

    @Builder
    public HomepageInstaDto(String admin, String url, String img) {
        this.admin = admin;
        this.url = url;
        this.img = img;
    }

}
