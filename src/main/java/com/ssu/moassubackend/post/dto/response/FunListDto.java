package com.ssu.moassubackend.post.dto.response;

import com.ssu.moassubackend.domain.post.Fun;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FunListDto {

    private Long id;
    private String title;
    private String url;
    private String thumbnail;

    @Builder
    public FunListDto(Long id, String title, String url, String thumbnail) {
        this.id = id;
        this.title = title;
        this.url = url;
        this.thumbnail = thumbnail;
    }

    public FunListDto(Fun page) {
        this.id = page.getId();
        this.title = page.getTitle();
        this.url = page.getSsu_link();
        this.thumbnail = page.getCover();
    }

}
